package jbsae.struct;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class Queue<T> implements Iterable<T>{
    public QueueIterator i1, i2;
    public T[] items;
    public int head, tail, size;

    public Queue(){
        items = (T[])new Object[4];
        i1 = new QueueIterator();
        i2 = new QueueIterator();
    }

    public Queue(Object... values){
        this();
        for(Object value : values) addLast((T)value);
    }

    public Object[] list(){
        int i = 0;
        Object[] values = create(size);
        for(T value : this) values[i++] = value;
        return values;
    }

    public void addFirst(T value){
        T[] items = this.items;
        if(size == items.length) items = resize(max(8, size * 2));
        head = mod(head - 1, items.length);
        items[head] = value;
        size++;
    }

    public void addLast(T value){
        T[] items = this.items;
        if(size == items.length) items = resize(max(8, size * 2));
        items[tail] = value;
        tail = mod(tail + 1, items.length);
        size++;
    }

    public void removeFirst(){
        T[] items = this.items;
        items[head] = null;
        head = mod(head + 1, items.length);
        size--;
    }

    public void removeLast(){
        T[] items = this.items;
        tail = mod(tail - 1, items.length);
        items[tail] = null;
        size--;
    }

    public T get(int index){
        return items[mod((head + index), items.length)];
    }

    public T first(){
        return get(0);
    }

    public T last(){
        return get(size - 1);
    }

    public boolean contains(T value){
        for(int i = 0;i < size;i++) if(eql(get(i), value)) return true;
        return false;
    }

    public void trim(){
        resize(size);
    }

    public T[] resize(int newSize){
        T[] items = this.items;
        T[] newItems = create(newSize, items);
        for(int i = 0;i < size;i++) newItems[i] = get(i);
        this.items = newItems;
        head = 0;
        tail = size;
        return newItems;
    }

    @Override
    public Iterator<T> iterator(){
        if(i1.index >= size){
            i1.index = 0;
            return i1;
        }
        if(i2.index >= size){
            i2.index = 0;
            return i2;
        }
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<T>{
        public int index;

        public QueueIterator(){
        }

        @Override
        public boolean hasNext(){
            return index < size;
        }

        @Override
        public T next(){
            return get(index++);
        }
    }
}
