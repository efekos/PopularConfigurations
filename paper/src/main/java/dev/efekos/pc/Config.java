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

import java.util.List;

/**
 * Represents a configuration file.
 */
public interface Config {

    /**
     * Tries to find a list of strings in this config. Will return an empty list if the list wasn't found.
     *
     * @param key Input key.
     * @return Whatever found.
     */
    List<String> getStringList(String key);

    /**
     * Tries to find a string in this config.
     *
     * @param key Input key.
     * @param def Default value to return if the result was {@code null}.
     * @return Whatever found.
     */
    String getString(String key, String def);

    /**
     * Tries to find a boolean in this config.
     *
     * @param key Input key.
     * @param def Default value to return if the result was {@code null}.
     * @return Whatever found.
     */
    boolean getBoolean(String key, boolean def);

    /**
     * Tries to find an integer in this config.
     *
     * @param key Input key.
     * @param def Default value to return if the result was {@code null}.
     * @return Whatever found.
     */
    int getInteger(String key, int def);

    /**
     * Tries to find a double in this config.
     *
     * @param key Input key.
     * @param def Default value to return if the result was {@code null}.
     * @return Whatever found.
     */
    double getDouble(String key, double def);

    /**
     * Reloads the configuration from its specified files.
     */
    void reload();

    /**
     * Overrides the configuration with the default config. Can be used for restoration and debug purposes.
     */
    void override();

    /**
     * Creates a message provider from this config.
     *
     * @return New message provider.
     */
    default MessageProvider asMessageProvider() {
        return new ConfigMessageProvider(this);
    }

}
