package datastructure;

import java.util.*;

/**
 * Created by Haochen on 2017/2/15.
 * TODO:
 */
public class MyArrayList<T> implements List<T> {

    private Object[] array;
    private int size;

    public MyArrayList() {
        this.array = new Object[10];
    }

    public MyArrayList(int initSize) {
        this.array = new Object[initSize];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = -1;
            @Override
            public boolean hasNext() {
                return index < size - 1;
            }

            @Override
            public T next() {
                index++;
                return (T) array[index];
            }

            @Override
            public void remove() {
                MyArrayList.this.remove(index);
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(array, 0, result, 0, size);
        return result;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return (T1[]) Arrays.copyOf(array, size, a.getClass());
        } else {
            System.arraycopy(array, 0, a, 0, size);
            return a;
        }
    }

    private boolean isFull() {
        return size >= array.length;
    }

    private void expand(int newCapacity) {
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        this.array = newArray;
    }

    @Override
    public boolean add(T t) {
        if (isFull()) {
            expand(size * 2);
        }
        array[size] = t;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        addAll(size, c);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        int addCount = c.size();
        int capacityAfterAdd = array.length;
        int countAfterAdd = size + addCount;
        while (capacityAfterAdd < countAfterAdd) {
            capacityAfterAdd *= 2;
        }
        if (capacityAfterAdd > array.length) {
            expand(capacityAfterAdd);
        }
        System.arraycopy(array, index, array, index + addCount, size - index);
        for (Object o : c) {
            array[size] = o;
            size++;
        }
        return true;
    }

    private void removeAll(boolean[] willRemoved) {
        int moveCount = 0;
        for (int i = 0; i < size; ++i) {
            if (willRemoved[i]) {
                moveCount++;
            } else {
                array[i - moveCount] = array[i];
            }
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean[] willRemoved = new boolean[size];
        for (int i = 0; i < size; ++i) {
            willRemoved[i] = c.contains(array[i]);
        }
        removeAll(willRemoved);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean[] willRemoved = new boolean[size];
        for (int i = 0; i < size; ++i) {
            willRemoved[i] = !c.contains(array[i]);
        }
        removeAll(willRemoved);
        return false;
    }

    @Override
    public void clear() {
        array = new Object[10];
        size = 0;
    }

    @Override
    public T get(int index) {
        return (T) array[index];
    }

    @Override
    public T set(int index, T element) {
        array[index] = element;
        return element;
    }

    @Override
    public void add(int index, T element) {
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
    }

    @Override
    public T remove(int index) {
        T t = (T) array[index];
        System.arraycopy(array, index + 1, array, index, size - index);
        return t;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; ++i) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size; i >= 0; --i) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        final int beginIndex = index;
        return new ListIterator<T>() {
            private int point = beginIndex - 1;
            @Override
            public boolean hasNext() {
                return point < size;
            }

            @Override
            public T next() {
                point++;
                return (T) array[point];
            }

            @Override
            public boolean hasPrevious() {
                return point > beginIndex;
            }

            @Override
            public T previous() {
                point--;
                return (T) array[point];
            }

            @Override
            public int nextIndex() {
                return point + 1;
            }

            @Override
            public int previousIndex() {
                return point - 1;
            }

            @Override
            public void remove() {
                MyArrayList.this.remove(point);
            }

            @Override
            public void set(T t) {
                MyArrayList.this.set(point, t);
            }

            @Override
            public void add(T t) {
                MyArrayList.this.add(point, t);
            }
        };
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        MyArrayList<T> result = new MyArrayList<>(toIndex - fromIndex + 1);
        for (int i = fromIndex; i <= toIndex; ++i) {
            result.array[result.size] = array[i];
            result.size++;
        }
        return result;
    }
}
