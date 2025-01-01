/*
 * MIT License
 *
 * Copyright (c) 2025 efekos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.efekos.pc;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigMessageProvider implements MessageProvider {

    public static final Pattern hexPattern = Pattern.compile("(&#[0-9a-fA-F]{6})");
    private final Config config;
    private String prefix;

    public ConfigMessageProvider(Config config) {
        this.config = config;
    }

    private String translate(String message) {
        String newMessage = message;

        Matcher matcher = hexPattern.matcher(newMessage);
        StringBuilder buffer = new StringBuilder();
        while (matcher.find()) matcher.appendReplacement(buffer, ChatColor.of(matcher.group(1).substring(1)) + "");
        matcher.appendTail(buffer);

        newMessage = ChatColor.translateAlternateColorCodes('&', buffer.toString());
        return newMessage;
    }

    @Override
    public String formatRaw(String key, Placeholder... placeholders) {
        String f = "&r" + config.getString(key, "&c" + key);
        for (Placeholder placeholder : placeholders) f = placeholder.replace(f);
        return translate(f);
    }

    @Override
    public List<String> formatListRaw(String key, MessagePortionHider hider, Placeholder... placeholders) {
        List<String> list = config.getStringList(key);
        ArrayList<String> newList = new ArrayList<>();
        for (String s : list)
            if (!hider.shouldHide(s)) {
                String s1 = "&r" + s.replaceFirst("^\\$\\?([A-Z0-9]+(?:,[A-Z0-9]+)*):", "");
                for (Placeholder placeholder : placeholders) s1 = placeholder.replace(s1);
                newList.add(translate(s1));
            }
        return newList;
    }

    @Override
    public boolean hasPrefix() {
        return prefix == null;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
