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

import java.util.Map;
import java.util.TreeMap;

public class ConfigObject implements ConfigElement {

    private final Map<String, ConfigElement> compoundMap = new TreeMap<>();

    public ConfigObject() {
    }

    @Override
    public boolean isCompound() {
        return true;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    public void set(String key, ConfigElement tag) {
        compoundMap.put(key, tag);
    }

    public void remove(String key) {
        compoundMap.remove(key);
    }

    public void setString(String key, String value) {
        set(key, ConfigString.of(value));
    }

    public void setBool(String key, boolean value) {
        set(key, ConfigBoolean.of(value));
    }

    public void setInt(String key, int value) {
        set(key, ConfigInteger.of(value));
    }

    public void setLong(String key, long value) {
        set(key, ConfigInteger.of(value));
    }

    public void setFloat(String key, float value) {
        set(key, ConfigFloat.of(value));
    }

    public void setDouble(String key, double value) {
        set(key, ConfigDouble.of(value));
    }

    public String getString(String key) {
        return contains(key, STRING_TAG) ? (String) get(key, STRING_TAG) : null;
    }

    public Boolean getBool(String key) {
        return contains(key, BOOL_TAG) ? (Boolean) get(key, BOOL_TAG) : null;
    }

    public Integer getInt(String key) {
        return contains(key, INT_TAG) ? (Integer) get(key, INT_TAG) : null;
    }

    public Long getLong(String key) {
        return contains(key, LONG_TAG) ? (Long) get(key, LONG_TAG) : null;
    }

    public Float getFloat(String key) {
        return contains(key, FLOAT_TAG) ? (Float) get(key, FLOAT_TAG) : null;
    }

    public Double getDouble(String key) {
        return contains(key, DOUBLE_TAG) ? (Double) get(key, DOUBLE_TAG) : null;
    }

    public ConfigObject getCompound(String key) {
        return contains(key, COMPOUND_TAG) ? ((ConfigObject) get(key, COMPOUND_TAG)) : null;
    }

    public Object get(String key, int type) {
        if (!compoundMap.containsKey(key)) return null;
        ConfigElement tag = compoundMap.get(key);
        return switch (type) {
            case 0 -> tag.getAsBoolean();
            case 1 -> tag.getAsInt();
            case 2 -> tag.getAsString();
            case 3 -> tag.getAsFloat();
            case 4 -> tag.getAsDouble();
            case 5 -> tag.getAsLong();
            case 6 -> tag.getAsCompound();
            case 7 -> tag.getAsArray();
            default -> throw new IllegalArgumentException("Unknown tag type");
        };
    }

    public ConfigElement get(String key) {
        return compoundMap.get(key);
    }

    public ConfigArray<?> getArray(String key) {
        if (!contains(key, ARRAY_TYPE)) return null;
        return (ConfigArray<?>) get(key, ARRAY_TYPE);
    }

    public boolean contains(String key) {
        return compoundMap.containsKey(key);
    }

    public boolean contains(String key, int type) {
        return contains(key) && get(key).getTagType() == type;
    }

    @Override
    public boolean getAsBoolean() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getAsDouble() {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getAsFloat() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAsInt() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAsLong() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAsString() {
        return this.toString();
    }

    @Override
    public ConfigObject getAsCompound() {
        return this;
    }

    @Override
    public String toString() {
        return compoundMap.toString();
    }

    @Override
    public int getTagType() {
        return COMPOUND_TAG;
    }

    @Override
    public ConfigArray<?> getAsArray() {
        throw new UnsupportedOperationException();
    }

}
