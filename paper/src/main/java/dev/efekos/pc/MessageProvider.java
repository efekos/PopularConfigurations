package dev.efekos.pc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.List;

/**
 * Represents a message provider that can translate color codes and apply placeholders.
 */
public interface MessageProvider {

    /**
     * Formats the message with the given key.
     * @param key Input key.
     * @param placeholders Placeholders.
     * @return Formatted text.
     */
    Component format(String key, TagResolver... placeholders);

    /**
     * Formats a list of messages using the given key.
     * @param key Input key.
     * @param hider Message line hider, used to hide specific parts of the message list depending on the context.
     * @param placeholders Placeholders.
     * @return Formatted text.
     */
    List<Component> formatList(String key, MessagePortionHider hider, TagResolver... placeholders);

}