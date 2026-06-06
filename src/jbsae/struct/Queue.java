package jbsae.struct;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;

public class Queue<T> implements Iterable<T>{
    private T[] items;
    private int head, tail, end;

    public int size;


    public Queue(){
        this(16);
    }

    public Queue(int capacity){
        items = (T[])new Object[capacity];
        tail = end = capacity - 1;
        head = 0;
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

    public Queue<T> set(int index, T value){
        items[trueIndex(index)] = value;
        return this;
    }

    public Queue<T> addFirst(T value){
        if(size >= items.length) resize(items.length + (items.length >> 1) + 1);
        head = decrement(head);
        items[head] = value;
        size++;
        return this;
    }

    public Queue<T> addAllFirst(Iterator<T> itr){
        if(itr instanceof Sized list) ensure(list.size());
        while(itr.hasNext()) addFirst(itr.next());
        return this;
    }

    public Queue<T> addAllFirst(Iterable<T> values){
        return addAllFirst(values.iterator());
    }

    public Queue<T> addLast(T value){
        if(size >= items.length) resize(items.length + (items.length >> 1) + 1);
        tail = increment(tail);
        items[tail] = value;
        size++;
        return this;
    }

    public Queue<T> addAllLast(Iterator<T> itr){
        if(itr instanceof Sized list) ensure(list.size());
        while(itr.hasNext()) addLast(itr.next());
        return this;
    }

    public Queue<T> addAllLast(Iterable<T> values){
        return addAllLast(values.iterator());
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


    public T get(int index){
        return items[trueIndex(index)];
    }

    public T first(){
        return items[head];
    }

    public T last(){
        return items[tail];
    }

    public Queue<T> clear(){
        int n = min(items.length - head, size);
        Arrays.fill(items, head, head + n, null);
        Arrays.fill(items, 0, size - n, null);
        head = size = 0;
        tail = end;
        return this;
    }

    public Queue<T> ensure(int space){
        if(size + space >= items.length) resize(size + space + 1);
        return this;
    }

    public Queue<T> resize(int capacity){
        T[] old = items;
        items = (T[])new Object[capacity];
        int n = min(old.length - head, size);
        System.arraycopy(old, head, items, 0, n);
        System.arraycopy(old, 0, items, n, size - n);
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
        return itrToString(iterator());
    }

    private class QueueIterator implements Iterator<T>, Sized{
        public int index = 0;

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

        @Override
        public int size(){
            return size;
        }
    }
}