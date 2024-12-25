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

public class ConfigInteger implements ConfigElement {

    private final Integer _int;

    public ConfigInteger(Integer _int) {
        this._int = _int;
    }

    public static ConfigInteger of(int i) {
        return new ConfigInteger(i);
    }

    public static ConfigInteger of(long i) {
        return new ConfigInteger(Math.min(Integer.MAX_VALUE, (int) Math.max(Integer.MIN_VALUE, i)));
    }

    public static ConfigInteger of(double s) {
        return new ConfigInteger((int) Math.floor(s));
    }

    public Integer getInt() {
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
        return _int > 0;
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
        return _int;
    }

    @Override
    public long getAsLong() {
        return (long) _int;
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
        return INT_TAG;
    }

    @Override
    public ConfigArray<?> getAsArray() {
        throw new UnsupportedOperationException();
    }
}
