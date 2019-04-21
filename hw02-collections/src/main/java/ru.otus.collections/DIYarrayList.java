package ru.otus.collections;

import java.util.*;

public class DIYarrayList<T> implements List<T> {

    private static final String EXC_MSG_NOT_IMPL = "Not implemented yet... Under construction";
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_DIYELEMENTDATA = {};
    private int size = 0;
    private Object[] diyElementData;

    public DIYarrayList() {
        this.diyElementData = new Object[DEFAULT_CAPACITY];
    }

    public DIYarrayList(int capacity) {

        if (capacity < 0) throw new IllegalArgumentException("Wrong value of parameter capacity: " + capacity);
        this.diyElementData = (capacity == 0) ? EMPTY_DIYELEMENTDATA : new Object[capacity];

    }

    public DIYarrayList(Collection<? extends T> c) {
        diyElementData = c.toArray();
        if ((size = diyElementData.length) != 0) {
            // defend against c.toArray (incorrectly) not returning Object[]
            // (see e.g. https://bugs.openjdk.java.net/browse/JDK-6260652)
            if (diyElementData.getClass() != Object[].class)
                diyElementData = Arrays.copyOf(diyElementData, size, Object[].class);
        } else {
            // replace with empty array.
            this.diyElementData = new Object[]{};
        }
    }

    public static <T> boolean addAll(Collection<? super T> c, T... elements) {
        boolean result = false;
        for (T element : elements)
            result |= c.add(element);
        return result;
    }

    @Override
    public boolean add(T e) {


        if (size == diyElementData.length)
            //increase size
            diyElementData = extendElementData(size + 1);
        diyElementData[size] = e;
        size++;
        return true;
    }

    public void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity > diyElementData.length || requiredCapacity <= DEFAULT_CAPACITY) {
            diyElementData = extendElementData(requiredCapacity);
        }
    }

    public Object[] extendElementData(int requiredCapacity) {
        /*return elementData = Arrays.copyOf(elementData,
                newCapacity(minCapacity));*/

        int maxSize = Integer.MAX_VALUE - 8;
        int newCapacity = diyElementData.length * 3 / 2 + 1;
        boolean isOutOfMemory = (newCapacity - maxSize) > 0;

        if (!isOutOfMemory) {
            return Arrays.copyOf(diyElementData, newCapacity);
        } else return
                (requiredCapacity > maxSize) ?
                        Arrays.copyOf(diyElementData, Integer.MAX_VALUE)
                        : Arrays.copyOf(diyElementData, maxSize);


    }

    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    public static <T> void sort(List<T> list, Comparator<? super T> c) {
        list.sort(c);
    }

    @Override
    public int size() {
        {
            return size;
        }
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    @Override
    public Iterator<T> iterator() {

        return (Iterator<T>) Arrays.asList(Arrays.copyOf(diyElementData, size)).iterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(diyElementData, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {

        return (T[]) Arrays.copyOf(diyElementData, size);
    }

    /*   public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
           @SuppressWarnings("unchecked")
           T[] copy = ((Object)newType == (Object)Object[].class)
                   ? (T[]) new Object[newLength]
                   : (T[]) Array.newInstance(newType.getComponentType(), newLength);
           System.arraycopy(original, 0, copy, 0,
                   Math.min(original.length, newLength));
           return copy;
           */

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T) diyElementData[index];
    }

    @Override
    public T set(int index, T element) {
        Objects.checkIndex(index, size);
        diyElementData[index] = element;
        return (T) diyElementData[index];
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
    }

    @Override
    public ListIterator<T> listIterator() {
        return (ListIterator<T>) Arrays.asList(diyElementData).listIterator();

    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return (ListIterator<T>) Arrays.asList(diyElementData).listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size) throw new IndexOutOfBoundsException("Index out of bounds ");
        if (fromIndex > toIndex) throw new IllegalArgumentException(" Illegal index from and to parameters");


        return (List<T>) Arrays.asList(Arrays.copyOfRange(diyElementData, fromIndex, toIndex));
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size(); i++) {

            s.append(diyElementData[i] == null ? "{}" : diyElementData[i].toString()).append(" ");
        }
        return s.toString();
    }
}
