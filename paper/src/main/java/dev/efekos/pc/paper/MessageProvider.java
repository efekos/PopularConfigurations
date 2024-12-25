package dev.efekos.pc.paper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.List;

public interface MessageProvider {

    Component format(String key, TagResolver... placeholders);
    List<Component> formatList(String key, MessagePortionHider hider, TagResolver... placeholders);

}