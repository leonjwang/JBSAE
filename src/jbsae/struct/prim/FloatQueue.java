package jbsae.struct.prim;

import jbsae.struct.*;
import jbsae.struct.prim.iterator.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;

public class FloatQueue{
    private float[] items;
    private int head, tail, end;

    public int size;

    public FloatQueue(){
        this(16);
    }

    public FloatQueue(int capacity){
        items = new float[capacity];
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

    public FloatQueue set(int index, float value){
        items[trueIndex(index)] = value;
        return this;
    }

    public FloatQueue addFirst(float value){
        if(size >= items.length) resize(items.length + (items.length >> 1) + 1);
        head = decrement(head);
        items[head] = value;
        size++;
        return this;
    }

    public FloatQueue addAllFirst(FloatIterator itr){
        if(itr instanceof Sized list) ensure(list.size());
        while(itr.hasNext()) addFirst(itr.next());
        return this;
    }

    public FloatQueue addLast(float value){
        if(size >= items.length) resize(items.length + (items.length >> 1) + 1);
        tail = increment(tail);
        items[tail] = value;
        size++;
        return this;
    }

    public FloatQueue addAllLast(FloatIterator itr){
        if(itr instanceof Sized list) ensure(list.size());
        while(itr.hasNext()) addLast(itr.next());
        return this;
    }

    public FloatQueue removeFirst(){
        head = increment(head);
        size--;
        return this;
    }

    public FloatQueue removeLast(){
        tail = decrement(tail);
        size--;
        return this;
    }

    public float popFirst(){
        float value = first();
        removeFirst();
        return value;
    }

    public float popLast(){
        float value = last();
        removeLast();
        return value;
    }

    public float get(int index){
        return items[trueIndex(index)];
    }

    public float first(){
        return items[head];
    }

    public float last(){
        return items[tail];
    }

    public FloatQueue clear(){
        head = size = 0;
        tail = end;
        return this;
    }

    public FloatQueue ensure(int space){
        if(size + space >= items.length) resize(size + space + 1);
        return this;
    }

    public FloatQueue resize(int capacity){
        float[] old = items;
        items = new float[capacity];
        int n = min(old.length - head, size);
        System.arraycopy(old, head, items, 0, n);
        System.arraycopy(old, 0, items, n, size - n);
        head = 0;
        tail = size - 1;
        end = capacity - 1;
        return this;
    }

    public FloatIterator iterator(){
        return new QueueIterator();
    }

    @Override
    public String toString(){
        return itrToString(iterator());
    }

    private class QueueIterator extends FloatIterator implements Sized{
        public int index = 0;

        public QueueIterator(){
        }

        @Override
        public boolean hasNext(){
            return index < size;
        }

        @Override
        public float nextf(){
            return get(index++);
        }

        @Override
        public int size(){
            return size;
        }
    }
}