package dev.efekos.pc;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigMessageProvider implements MessageProvider {

    public static final Pattern hexPattern = Pattern.compile("(&#[0-9a-fA-F]{6})");
    private final Config config;

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
    public String format(String key, Placeholder... placeholders) {
        String f = "&r"+config.getString(key, "&c" + key);
        for (Placeholder placeholder : placeholders) f = placeholder.replace(f);
        return translate(f);
    }

    @Override
    public List<String> formatList(String key, MessagePortionHider hider, Placeholder... placeholders) {
        List<String> list = config.getStringList(key);
        ArrayList<String> newList = new ArrayList<>();
        for (String s : list)
            if (!hider.shouldHide(s)) {
                String s1 = "&r"+s.replaceFirst("^\\$\\?([A-Z0-9]+(?:,[A-Z0-9]+)*):", "");
                for (Placeholder placeholder : placeholders) s1 = placeholder.replace(s1);
                newList.add(translate(s1));
            }
        return newList;
    }

}
