/*
 * MIT License
 *
 * Copyright (c) 2024 efekos
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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ConfigMessageProvider implements MessageProvider {

    private final Config config;
    private final MiniMessage message = MiniMessage.miniMessage();

    public ConfigMessageProvider(Config config) {
        this.config = config;
    }

    private Component translate(String message, TagResolver... resolvers) {
        return this.message.deserialize(message, resolvers);
    }

    @Override
    public Component format(String key, TagResolver... resolvers) {
        return translate("<!italic>" + config.getString(key, "<red>" + key + "</red>"), resolvers);
    }

    @Override
    public List<Component> formatList(String key, MessagePortionHider hider, TagResolver... placeholders) {
        List<String> list = config.getStringList(key);
        ArrayList<Component> newList = new ArrayList<>();
        for (String s : list)
            if (!hider.shouldHide(s)) {
                String s1 = s.replaceFirst("^\\$\\?([A-Z0-9]+(?:,[A-Z0-9]+)*):", "");
                newList.add(translate("<!italic>" + s1, placeholders));
            }
        return newList;
    }

}
