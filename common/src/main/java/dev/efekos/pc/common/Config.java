package dev.efekos.pc.common;

import dev.efekos.pc.common.tag.ConfigObject;

public interface Config {

    ConfigObject getRaw();
    <V> V get(String key, ConfigValueType<V> type);
    <V> void set(String key, ConfigValueType<V> type, V value);

}