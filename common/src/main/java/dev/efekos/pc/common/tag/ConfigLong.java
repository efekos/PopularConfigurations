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

public class ConfigLong implements ConfigElement {

    private final Long _int;

    public ConfigLong(Long _int) {
        this._int = _int;
    }

    public static ConfigLong of(int i) {
        return new ConfigLong((long) i);
    }

    public static ConfigLong of(long i) {
        return new ConfigLong(i);
    }

    public static ConfigLong of(double s) {
        return new ConfigLong((long) Math.floor(s));
    }

    public Long getLong() {
        return _int;
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
        return false;
    }

    @Override
    public double getAsDouble() {
        return (double) _int;
    }

    @Override
    public float getAsFloat() {
        return (float) _int;
    }

    @Override
    public int getAsInt() {
        return _int == null ? 0 : _int.intValue();
    }

    @Override
    public long getAsLong() {
        return _int;
    }

    @Override
    public String getAsString() {
        return "" + _int;
    }

    @Override
    public ConfigObject getAsCompound() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getTagType() {
        return LONG_TAG;
    }

    @Override
    public ConfigArray<?> getAsArray() {
        throw new UnsupportedOperationException();
    }
}
