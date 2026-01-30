package jbsae.struct;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;

public class Queue<T> implements List<T>{
    private T[] items;
    private int head, tail, end;

    public int size;


    public Queue(){
        this(16);
    }

    public Queue(int capacity){
        items = create(capacity);
        tail = end = capacity - 1;
        head = 0;
    }

    public Queue(T[] items){
        this(items.length);
        for(int i = 0;i < items.length;i++) this.items[i] = (T)items[i];
        head = 0;
        tail = end = items.length - 1;
        size = items.length;
    }

    @Override
    public Object[] list(){
        Object[] values = new Object[size];
        int n = min(items.length - head, size);
        copy(items, head, values, 0, n);
        copy(items, 0, values, n, size - n);
        return values;
    }

    private int increment(int index){
        return (index == end) ? 0 : (index + 1);
    }

    private int decrement(int index){
        return (index == 0) ? end : (index - 1);
    }

    private int trueIndex(int index){
        return (head + index < items.length) ? (head + index) : (head + index - items.length);
    }

    @Override
    public List<T> set(int index, T value){
        items[trueIndex(index)] = value;
        return this;
    }


    public Queue<T> addFirst(T value){
        if(value == null) throw new RuntimeException("Value is null");
        if(size >= items.length) resize((int)(items.length * 1.5f + 1));
        head = decrement(head);
        items[head] = value;
        size++;
        return this;
    }

    public Queue<T> addLast(T value){
        if(value == null) throw new RuntimeException("Value is null");
        if(size >= items.length) resize((int)(items.length * 1.5f + 1));
        tail = increment(tail);
        items[tail] = value;
        size++;
        return this;
    }

    public Queue<T> removeFirst(){
        items[head] = null;
        head = increment(head);
        size--;
        return this;
    }

    public Queue<T> removeLast(){
        items[tail] = null;
        tail = decrement(tail);
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
        return items[trueIndex(index)];
    }

    public T first(){
        return items[head];
    }

    public T last(){
        return items[tail];
    }

    @Override
    public int size(){
        return size;
    }

    public Queue<T> clear(){
        int n = min(items.length - head, size);
        for(int i = 0;i < n;i++) items[head + i] = null;
        for(int i = n;i < size;i++) items[i - n] = null;
        head = size = 0;
        tail = end;
        return this;
    }

    public Queue<T> resize(int capacity){
        T[] old = items;
        items = create(capacity, items);
        int n = min(items.length - head, size);
        copy(old, head, items, 0, n);
        copy(old, 0, items, n, size - n);
        head = 0;
        tail = size - 1;
        end = capacity - 1;
        return this;
    }


    @Override
    public Iterator<T> iterator(){
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
