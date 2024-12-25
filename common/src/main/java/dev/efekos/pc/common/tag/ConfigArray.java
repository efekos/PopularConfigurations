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

import java.util.*;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class ConfigArray<T extends ConfigElement> implements ConfigElement, Iterable<T> {

    private final List<T> tags;

    public ConfigArray(List<T> tags) {
        this.tags = tags;
    }

    @SafeVarargs
    public static <T extends ConfigElement> ConfigArray<T> of(T... tags) {
        return new ConfigArray<>(List.of(tags));
    }

    public static <T extends ConfigElement> ConfigArray<T> of(List<T> tags) {
        return new ConfigArray<>(tags);
    }

    public static <T extends ConfigElement> ConfigArray<T> empty() {
        return new ConfigArray<>(new ArrayList<>());
    }

    public static <T extends ConfigElement> ConfigArray<T> of(Stream<T> tags) {
        return new ConfigArray<>(tags.toList());
    }

    public int size() {
        return tags.size();
    }

    public ListIterator<T> listIterator() {
        return tags.listIterator();
    }

    public void clear() {
        tags.clear();
    }

    public ListIterator<T> listIterator(int index) {
        return tags.listIterator(index);
    }

    public <T1> T1[] toArray(T1[] a) {
        return tags.toArray(a);
    }

    public int lastIndexOf(Object o) {
        return tags.lastIndexOf(o);
    }

    public T removeLast() {
        return tags.removeLast();
    }

    public void sort(Comparator<? super T> c) {
        tags.sort(c);
    }

    public List<T> reversed() {
        return tags.reversed();
    }

    public <T1> T1[] toArray(IntFunction<T1[]> generator) {
        return tags.toArray(generator);
    }

    public Object[] toArray() {
        return tags.toArray();
    }

    public void replaceAll(UnaryOperator<T> operator) {
        tags.replaceAll(operator);
    }

    public int indexOf(Object o) {
        return tags.indexOf(o);
    }

    public T removeFirst() {
        return tags.removeFirst();
    }

    public boolean contains(Object o) {
        return tags.contains(o);
    }

    public T getFirst() {
        return tags.getFirst();
    }

    public T getLast() {
        return tags.getLast();
    }

    public boolean retainAll(Collection<?> c) {
        return tags.retainAll(c);
    }

    public Stream<T> stream() {
        return tags.stream();
    }

    public void addLast(T t) {
        tags.addLast(t);
    }

    public Stream<T> parallelStream() {
        return tags.parallelStream();
    }

    public boolean isEmpty() {
        return tags.isEmpty();
    }

    public boolean removeAll(Collection<?> c) {
        return tags.removeAll(c);
    }

    public void add(int index, T element) {
        tags.add(index, element);
    }

    public T set(int index, T element) {
        return tags.set(index, element);
    }

    public void addFirst(T t) {
        tags.addFirst(t);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return tags.addAll(index, c);
    }

    public boolean containsAll(Collection<?> c) {
        return new HashSet<>(tags).containsAll(c);
    }

    public T get(int index) {
        return tags.get(index);
    }

    public boolean addAll(Collection<? extends T> c) {
        return tags.addAll(c);
    }

    public boolean remove(Object o) {
        return tags.remove(o);
    }

    public boolean removeIf(Predicate<? super T> filter) {
        return tags.removeIf(filter);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return tags.subList(fromIndex, toIndex);
    }

    public void add(T tag) {
        tags.add(tag);
    }

    public void remove(T tag) {
        tags.remove(tag);
    }

    public void remove(int index) {
        tags.remove(index);
    }

    @Override
    public boolean isCompound() {
        return false;
    }

    @Override
    public boolean isArray() {
        return true;
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
        return tags.toString();
    }

    @Override
    public ConfigObject getAsCompound() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getTagType() {
        return ARRAY_TYPE;
    }

    @Override
    public Iterator<T> iterator() {
        return new ConfigElementIterator<>(tags);
    }

    @Override
    public ConfigArray<?> getAsArray() {
        return this;
    }
}
