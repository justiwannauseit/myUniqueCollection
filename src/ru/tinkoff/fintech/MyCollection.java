package ru.tinkoff.fintech;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyCollection<E> implements Collection<E> {

    private int size;
    private Object[] elementData = new Object[10];

    /**
     * @param e - добавляемый объект в колекцию.
     * @return результат добавления в колекцию (true).
     */
    @Override
    public boolean add(final E e) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) (size * 1.5f));
        }
        elementData[size++] = e;
        return true;
    }

    /**
     * @return размер массива.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * @return результат проверки пустая ли колекция.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return перебранная колекция.
     */
    @Override
    public Iterator<E> iterator() {
        return new MyIterator<>();
    }

    /**
     * Прооверяет наличие элемента в колекции.
     * @param o - принимаемый объект, совпадение с которым пытаемся найти.
     * @return результат проверки.
     */
    @Override
    public boolean contains(final Object o) {
        for (int i = 0; i < size; i++) {
            if (o == elementData[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return колекию, в виде массива.
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    /**
     * @param a - колекция, примнимаемая на вход.
     * @param <T> - тип колекции.
     * @return колекию в виде массива.
     */
    @Override
    public <T> T[] toArray(final T[] a) {
        if (a == null) {
            throw new IllegalArgumentException(" Массив не может быть null ");
        }
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        } else {
            System.arraycopy(elementData, 0, a, 0, size);
            Arrays.fill(a, size, a.length, null);
        }
        return a;
    }

    /**
     * Удаляет переданный элемент, если таковой имеется в колекции.
     * @param o - принимаемы обьект.
     * @return езультат работы методы.
     */
    @Override
    public boolean remove(final Object o) {
        for (int i = 0; i < size; i++) {
            if (elementData[i] == o) {
                System.arraycopy(elementData, i + 1, elementData, i, size - i - 1);
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     *  Метод проверяет есть ли элементы переданной колеции, в проверяемой колекии.
     * @param c - переданная колекция.
     * @return результат проверки.
     */
    @Override
    public boolean containsAll(final Collection<?> c) {
        boolean contain = false;
        for (Object t : c) {
            contain = false;
            for (Object j : elementData) {
                if (t == j) {
                    contain = true;
                }
            }
            if (!contain) {
                return false;
            }
        }
        return contain;
    }

    /**
     * Метод добавляет элементы переданной колекции в нашу колекцию.
     * @param c - передаваеемая колекция для добавления.
     * @return результат работы метода.
     */
    @Override
    public boolean addAll(final Collection<? extends E> c) {
        for (E i : c) {
            if (size == elementData.length) {
                elementData = Arrays.copyOf(elementData, (int) (size * 1.5f));
            }
            elementData[size++] = i;
        }
        return true;
    }


    /**
     * Метод удаляет совпадения элементов колекции с переданными элементами колекции.
     * @param c - передаваемая колекция.
     * @return результат работы метода.
     */
    @Override
    public boolean removeAll(final Collection<?> c) {
        boolean checkRemoveAll = false;
        for (Object t : c) {
            while (contains(t)) {
                checkRemoveAll = remove(t);
            }
        }
        return checkRemoveAll;

    }

    /**
     *  Метод проверяет элементы колекции и оставляет только те, которые совпали с элементами переданной колекции.
     * @param c - принимаемая колекция.
     * @return результат работы метода.
     */
    @Override
    public boolean retainAll(final Collection<?> c) {
        Object[] tmp = new Object[size];
        boolean checkRetainAll = false;
        int sizeTmp = 0;
        for (Object t : c) {
            for (int i = 0; i < size; i++) {
                if (t == elementData[i]) {
                    tmp[sizeTmp] = elementData[i];
                    sizeTmp++;
                    checkRetainAll = true;
                }
            }
        }
        if (sizeTmp == size) {
            return false;
        }
        if (checkRetainAll) {
            elementData = Arrays.copyOf(tmp, tmp.length);
            size = sizeTmp;
        } else {
            clear();
            return true;
        }
        return true;
    }

    /**
     */
    @Override
    public void clear() {
        size = 0;
        Arrays.fill(elementData, null);
    }


    private class MyIterator<T> implements Iterator<T> {

        int cursor = 0;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            MyCollection.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;

        }
    }
}