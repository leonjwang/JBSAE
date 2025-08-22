package jbsae.struct;

import jbsae.func.*;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;

public class Queue<T> implements List<T>{
    public QueueIterator i1, i2;
    public T[] items;
    public int head, tail, size;


    public Queue(){
        this(4);
    }

    public Queue(int size){
        items = (T[])new Object[size];
        i1 = new QueueIterator();
        i2 = new QueueIterator();
    }

    public Queue(Object... values){
        this(values.length);
        set(values);
    }

    public Queue(Iterable<T> values){
        this();
        for(T value : values) addLast(value);
    }


    @Override
    public Object[] list(){
        int i = 0;
        Object[] values = create(size);
        for(T value : this) values[i++] = value;
        return values;
    }


    @Override
    public List<T> set(T value, int index){
        items[mod((head + index), items.length)] = value;
        return this;
    }

    @Override
    public List<T> set(Object... values){
        clear();
        for(Object value : values) addLast((T)value);
        return this;
    }

    @Override
    public List<T> set(List<T> values){
        clear();
        for(T value : values) addLast(value);
        return this;
    }


    public Queue<T> addFirst(T value){
        if(value == null) return this;
        if(size == items.length) resize(max(8, size * 2));
        head = mod(head - 1, items.length);
        items[head] = value;
        size++;
        return this;
    }

    public Queue<T> addLast(T value){
        if(value == null) return this;
        if(size == items.length) resize(max(8, size * 2));
        items[tail] = value;
        tail = mod(tail + 1, items.length);
        size++;
        return this;
    }

    public Queue<T> removeFirst(){
        items[head] = null;
        head = mod(head + 1, items.length);
        size--;
        return this;
    }

    public Queue<T> removeLast(){
        tail = mod(tail - 1, items.length);
        items[tail] = null;
        size--;
        return this;
    }

    public T popFirst(){
        T value = first();
        removeFirst();
        return value;
    }

    public T popLast(){
        T value = last();
        removeLast();
        return value;
    }


    @Override
    public T get(int index){
        return items[mod((head + index), items.length)];
    }

    public T first(){
        return get(0);
    }

    public T last(){
        return get(size - 1);
    }

    @Override
    public int size(){
        return size;
    }


    public boolean contains(T value){
        for(int i = 0;i < size;i++) if(eql(get(i), value)) return true;
        return false;
    }

    public Queue<T> each(Cons<T> cons){
        for(int i = 0;i < size;i++) cons.get(get(i));
        return this;
    }

    public Queue<T> clear(){
        fill(items, null);
        head = tail = size = 0;
        return this;
    }

    public Queue<T> trim(){
        resize(size);
        return this;
    }

    public Queue<T> resize(int newSize){
        T[] items = create(newSize, this.items);
        for(int i = 0;i < size;i++) items[i] = get(i);
        this.items = items;
        head = 0;
        tail = size;
        return this;
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

    @Override
    public String toString(){
        return itrToString(this);
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
