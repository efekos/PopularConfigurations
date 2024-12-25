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

public interface ConfigElement {

    Integer BOOL_TAG = 0;
    Integer INT_TAG = 1;
    Integer STRING_TAG = 2;
    Integer FLOAT_TAG = 3;
    Integer DOUBLE_TAG = 4;
    Integer LONG_TAG = 5;
    Integer COMPOUND_TAG = 6;
    Integer ARRAY_TYPE = 7;

    boolean isCompound();

    boolean isArray();

    boolean getAsBoolean();

    double getAsDouble();

    float getAsFloat();

    int getAsInt();

    long getAsLong();

    String getAsString();

    ConfigObject getAsCompound();

    ConfigArray<?> getAsArray();

    int getTagType();

}
