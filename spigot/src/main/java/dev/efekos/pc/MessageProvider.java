package dev.efekos.pc;

import java.util.List;

public interface MessageProvider {

    String format(String key, Placeholder... placeholders);

    List<String> formatList(String key, MessagePortionHider hider, Placeholder... placeholders);

}