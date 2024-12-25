package dev.efekos.pc.paper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ConfigMessageProvider implements MessageProvider {

    public static final Pattern hexPattern = Pattern.compile("(&#[0-9a-fA-F]{6})");
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
