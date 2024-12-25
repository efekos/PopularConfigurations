package dev.efekos.pc.paper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigMessageProvider implements MessageProvider {

    private final Config config;

    public ConfigMessageProvider(Config config) {
        this.config = config;
    }

    public static final Pattern hexPattern = Pattern.compile("(&#[0-9a-fA-F]{6})");

    private final MiniMessage message = MiniMessage.miniMessage();

    private Component translate(String message, TagResolver... resolvers) {
        return this.message.deserialize(message,resolvers);
    }

    @Override
    public Component format(String key, TagResolver... resolvers) {
        return translate("<!italic>"+config.getString(key,"<red>"+key+"</red>"),resolvers);
    }

    @Override
    public List<Component> formatList(String key, MessagePortionHider hider, TagResolver... placeholders) {
        List<String> list = config.getStringList(key);
        ArrayList<Component> newList = new ArrayList<>();
        for (String s : list)
            if (!hider.shouldHide(s)) {
                String s1 = s.replaceFirst("^\\$\\?([A-Z0-9]+(?:,[A-Z0-9]+)*):", "");
                newList.add(translate("<!italic>"+s1,placeholders));
            }
        return newList;
    }

}
