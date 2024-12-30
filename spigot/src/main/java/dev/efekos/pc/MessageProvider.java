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

import java.util.List;

/**
 * Represents a message provider that can translate color codes and apply placeholders.
 */
public interface MessageProvider {

    /**
     * Formats the message with the given key. Will add the prefix if there is one (check with {@link #hasPrefix()}).
     *
     * @param key          Input key.
     * @param placeholders Placeholders.
     * @return Formatted text.
     */
    default String format(String key, Placeholder... placeholders) {
        return hasPrefix() ? getPrefix() + (formatRaw(key, placeholders)) : formatRaw(key);
    }

    /**
     * Formats a list of messages using the given key. Will add the prefix to every line if there is one.
     *
     * @param key          Input key.
     * @param hider        Message line hider, used to hide specific parts of the message list depending on the context.
     * @param placeholders Placeholders.
     * @return Formatted text.
     */
    default List<String> formatList(String key, MessagePortionHider hider, Placeholder... placeholders) {
        return hasPrefix() ? formatListRaw(key, hider, placeholders).stream().map(s -> getPrefix() + s).toList() : formatListRaw(key, hider, placeholders);
    }

    /**
     * Formats the message with the given key. Does not add the prefix.
     *
     * @param key          Input key.
     * @param placeholders Placeholders.
     * @return Formatted text.
     */
    String formatRaw(String key, Placeholder... placeholders);

    /**
     * Formats a list of messages using the given key. Does not add the prefix even if there is one.
     *
     * @param key          Input key.
     * @param hider        Message line hider, used to hide specific parts of the message list depending on the context.
     * @param placeholders Placeholders.
     * @return Formatted text.
     */
    List<String> formatListRaw(String key, MessagePortionHider hider, Placeholder... placeholders);

    /**
     * @return Whether this message provider has a prefix or not. The prefix is a message that is added to every message
     * formatted by this message provider.
     */
    boolean hasPrefix();

    /**
     * @return The prefix of this message provider.
     */
    String getPrefix();

    /**
     * Changes the prefix of this provider.
     *
     * @param prefix New prefix.
     */
    void setPrefix(String prefix);

}