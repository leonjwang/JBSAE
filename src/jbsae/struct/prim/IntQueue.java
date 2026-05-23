package jbsae.struct.prim;

import jbsae.struct.*;
import jbsae.struct.prim.iterator.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;

public class IntQueue{
    private int[] items;
    private int head, tail, end;

    public int size;

    public IntQueue(){
        this(16);
    }

    public IntQueue(int capacity){
        items = new int[capacity];
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

    public IntQueue set(int index, int value){
        items[trueIndex(index)] = value;
        return this;
    }

    public IntQueue addFirst(int value){
        if(size >= items.length) resize(items.length + (items.length >> 1) + 1);
        head = decrement(head);
        items[head] = value;
        size++;
        return this;
    }

    public IntQueue addAllFirst(IntIterator itr){
        if(itr instanceof Sized list) ensure(list.size());
        while(itr.hasNext()) addFirst(itr.next());
        return this;
    }

    public IntQueue addLast(int value){
        if(size >= items.length) resize(items.length + (items.length >> 1) + 1);
        tail = increment(tail);
        items[tail] = value;
        size++;
        return this;
    }

    public IntQueue addAllLast(IntIterator itr){
        if(itr instanceof Sized list) ensure(list.size());
        while(itr.hasNext()) addLast(itr.next());
        return this;
    }

    public IntQueue removeFirst(){
        head = increment(head);
        size--;
        return this;
    }

    public IntQueue removeLast(){
        tail = decrement(tail);
        size--;
        return this;
    }

    public int popFirst(){
        int value = first();
        removeFirst();
        return value;
    }

    public int popLast(){
        int value = last();
        removeLast();
        return value;
    }

    public int get(int index){
        return items[trueIndex(index)];
    }

    public int first(){
        return items[head];
    }

    public int last(){
        return items[tail];
    }

    public IntQueue clear(){
        head = size = 0;
        tail = end;
        return this;
    }

    public IntQueue ensure(int space){
        if(size + space >= items.length) resize(size + space + 1);
        return this;
    }

    public IntQueue resize(int capacity){
        int[] old = items;
        items = new int[capacity];
        int n = min(old.length - head, size);
        System.arraycopy(old, head, items, 0, n);
        System.arraycopy(old, 0, items, n, size - n);
        head = 0;
        tail = size - 1;
        end = capacity - 1;
        return this;
    }

    public IntIterator iterator(){
        return new QueueIterator();
    }

    @Override
    public String toString(){
        return itrToString(iterator());
    }

    private class QueueIterator extends IntIterator implements Sized{
        public int index = 0;

        public QueueIterator(){
        }

        @Override
        public boolean hasNext(){
            return index < size;
        }

        @Override
        public int nexti(){
            return get(index++);
        }

        @Override
        public int size(){
            return size;
        }
    }
}