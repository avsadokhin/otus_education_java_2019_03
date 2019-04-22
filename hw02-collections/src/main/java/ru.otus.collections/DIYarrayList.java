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

        if (capacity < 0)
            throw new IllegalArgumentException("Wrong value of parameter capacity: " + capacity + ". Capacity must be >=0");
        this.diyElementData = (capacity == 0) ? EMPTY_DIYELEMENTDATA : new Object[capacity];

    }

    public DIYarrayList(Collection<? extends T> c) {
        diyElementData = c.toArray();
        if ((size = diyElementData.length) != 0) {
            if (diyElementData.getClass() != Object[].class)
                diyElementData = Arrays.copyOf(diyElementData, size, Object[].class);
        } else {
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
        return new ListIteratorImpl(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListIteratorImpl(index);
    }


    private class ListIteratorImpl implements ListIterator<T> {
        int pos;
        int pos_returned = -1;

        ListIteratorImpl(int index) {
            pos = index;
        }

        @Override
        public boolean hasNext() {
            return pos != size;
        }

        @Override
        public T next() {
            if (pos > diyElementData.length)
                throw new NoSuchElementException("Position index " + pos + " is out of length " + diyElementData.length);
            if (pos > size) throw new IndexOutOfBoundsException("Position index " + pos + " is out of size " + size);

            pos_returned = pos;
            pos++;
            return (T) diyElementData[pos_returned];
        }

        @Override
        public boolean hasPrevious() {
            return pos != 0;
        }

        @Override
        public T previous() {
            int indx = pos - 1;
            if (indx < 0)
                throw new NoSuchElementException("No such element for position index " + indx);

            pos_returned = indx;
            pos = indx;

            return (T) diyElementData[pos_returned];
        }

        @Override
        public int nextIndex() {
            return pos;
        }

        @Override
        public int previousIndex() {
            return pos - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
        }

        @Override
        public void set(T t) {

            if (pos_returned < 0)
                throw new IndexOutOfBoundsException("Index cannot be < 0. Current value:" + pos_returned);

            DIYarrayList.this.set(pos_returned, t); //IndexOutOfBoundsException

        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException(EXC_MSG_NOT_IMPL);
        }
    }


    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size)
            throw new IndexOutOfBoundsException("Index out of bounds. fromIndex must be >=0, toIndex <= " + size);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException(" Illegal index from '" + fromIndex + "' and to '" + toIndex + "' parameters. fromIndex must be <= toIndex");


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
