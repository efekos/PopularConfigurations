/*
 * The MIT License (MIT)
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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package dev.efekos.pc.common.tag;

public class ConfigBoolean implements ConfigElement {

    private final Boolean value;

    ConfigBoolean(Boolean value) {
        this.value = value;
    }

    public static ConfigBoolean of(Boolean value) {
        return new ConfigBoolean(value);
    }

    public Boolean getBoolean() {
        return value;
    }

    @Override
    public boolean isCompound() {
        return false;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean getAsBoolean() {
        return value;
    }

    @Override
    public double getAsDouble() {
        return value ? 1 : 0;
    }

    @Override
    public float getAsFloat() {
        return value ? 1 : 0;
    }

    @Override
    public int getAsInt() {
        return value ? 1 : 0;
    }

    @Override
    public long getAsLong() {
        return value ? 1 : 0;
    }

    @Override
    public String getAsString() {
        return value ? "1b" : "0b";
    }

    @Override
    public ConfigObject getAsCompound() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getTagType() {
        return ConfigElement.BOOL_TAG;
    }

    @Override
    public ConfigArray<?> getAsArray() {
        throw new UnsupportedOperationException();
    }
}
