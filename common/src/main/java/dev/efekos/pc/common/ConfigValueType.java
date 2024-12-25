package dev.efekos.pc.common;

import dev.efekos.pc.common.tag.ConfigElement;
import dev.efekos.pc.common.tag.ConfigObject;

public interface ConfigValueType<T> {

    T serialize(ConfigObject compound);
    ConfigElement deserialize(T value);

}
