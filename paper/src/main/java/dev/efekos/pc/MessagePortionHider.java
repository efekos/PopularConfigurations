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

import javax.annotation.MatchesPattern;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.regex.Matcher;

public sealed interface MessagePortionHider permits MessagePortionHider.Impl, MessagePortionHider.List, MessagePortionHider.Empty, MessagePortionHider.Match {

    java.util.regex.Pattern PATTERN = java.util.regex.Pattern.compile("^\\$\\?([A-Z0-9]+(?:,[A-Z0-9]+)*):");

    private static java.util.List<String> tags(String input) {
        Matcher matcher = PATTERN.matcher(input);
        ArrayList<String> list = new ArrayList<>();
        while (matcher.find()) list.addAll(Arrays.asList(matcher.group(1).split(",")));
        return list;
    }

    static MessagePortionHider empty() {
        return new Empty();
    }

    static MessagePortionHider single(Predicate<String> predicate) {
        return new Impl(predicate);
    }

    @SafeVarargs
    static MessagePortionHider all(Predicate<String>... predicates) {
        ArrayList<MessagePortionHider> hiders = new ArrayList<>();
        for (Predicate<String> predicate : predicates) hiders.add(single(predicate));
        return new List(hiders, List.Mode.ALL);
    }

    @SafeVarargs
    static MessagePortionHider any(Predicate<String>... predicates) {
        ArrayList<MessagePortionHider> hiders = new ArrayList<>();
        for (Predicate<String> predicate : predicates) hiders.add(single(predicate));
        return new List(hiders, List.Mode.ANY);
    }

    static MessagePortionHider any(MessagePortionHider... predicates) {
        ArrayList<MessagePortionHider> hiders = new ArrayList<>();
        Collections.addAll(hiders, predicates);
        return new List(hiders, List.Mode.ANY);
    }

    static MessagePortionHider tag(@MatchesPattern("[A-Z0-9]+") String tag) {
        return new Match(tag);
    }

    boolean shouldHide(String message);

    default MessagePortionHider and(MessagePortionHider other) {
        if (other instanceof List) {
            ArrayList<MessagePortionHider> impls = new ArrayList<>(((List) other).impls);
            impls.add(this);
            return new List(impls, List.Mode.ALL);
        } else if (this instanceof List) {
            ArrayList<MessagePortionHider> impls = new ArrayList<>(((List) this).impls);
            impls.add(other);
            return new List(impls, List.Mode.ALL);
        } else return new List(java.util.List.of(this, other), List.Mode.ALL);
    }

    default MessagePortionHider or(MessagePortionHider other) {
        if (other instanceof List) {
            ArrayList<MessagePortionHider> impls = new ArrayList<>(((List) other).impls);
            impls.add(this);
            return new List(impls, List.Mode.ANY);
        } else if (this instanceof List) {
            ArrayList<MessagePortionHider> impls = new ArrayList<>(((List) this).impls);
            impls.add(other);
            return new List(impls, List.Mode.ANY);
        } else return new List(java.util.List.of(this, other), List.Mode.ALL);
    }

    record Match(String tag) implements MessagePortionHider {

        @Override
        public boolean shouldHide(String message) {
            return MessagePortionHider.tags(message).contains(tag);
        }

    }

    record Empty() implements MessagePortionHider {

        @Override
        public boolean shouldHide(String message) {
            return false;
        }

    }

    record Impl(@Nonnull Predicate<String> predicate) implements MessagePortionHider {
        @Override
        public boolean shouldHide(String message) {
            return predicate.test(message);
        }
    }

    record List(java.util.List<MessagePortionHider> impls, Mode mode) implements MessagePortionHider {

        @Override
        public boolean shouldHide(String message) {
            if (mode == Mode.ALL) return impls.stream().allMatch(impl -> impl.shouldHide(message));
            if (mode == Mode.ANY) return impls.stream().anyMatch(impl -> impl.shouldHide(message));
            return false;
        }

        enum Mode {
            ANY,
            ALL
        }

    }

}
