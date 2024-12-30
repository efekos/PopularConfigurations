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

package dev.efekos.pc.option;

import dev.efekos.pc.YamlConfig;
import org.bukkit.configuration.file.FileConfiguration;

public record YamlOptionLoader(YamlConfig config) implements OptionLoader {

    @Override
    public <T> T getOption(Option<T> option) {
        try {
            return config.get().contains(option.getKey(), true) ? option.getOptionType().deserialize(config.get().get(option.getKey())) : option.getDefaultValue();
        } catch (Exception | Error ignored) {
            return option.getDefaultValue();
        }
    }

    @Override
    public <T> void setOption(Option<T> option, T value) {
        FileConfiguration cf = config.get();
        cf.set(option.getKey(), option.getOptionType().serialize(value));
        config.save();
    }

    /**
     * @deprecated Use {@link #config()}.
     * @return The base config.
     */
    @Deprecated(forRemoval = true,since = "1.1.1")
    public YamlConfig getConfig() {
        return config;
    }

}
