package jbsae.struct.prim.base;

import jbsae.struct.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;

// TODO: Rewrite as well
public class ShortQueue{
    private static final short NULL = 0;

    private short[] items;
    private int head, tail, end;

    public int size;


    public ShortQueue(){
        this(16);
    }

    public ShortQueue(int capacity){
        items = new short[capacity];
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

    public ShortQueue set(int index, short value){
        items[trueIndex(index)] = value;
        return this;
    }

    public ShortQueue addFirst(short value){
        if(size >= items.length) resize((int)(items.length * 1.5f + 1));
        head = decrement(head);
        items[head] = value;
        size++;
        return this;
    }

    public ShortQueue addAllFirst(short[] list){
        ensure(list.length);
        for(int i = 0;i < list.length;i++) addFirst(list[i]);
        return this;
    }

    public ShortQueue addLast(short value){
        if(size >= items.length) resize((int)(items.length * 1.5f + 1));
        tail = increment(tail);
        items[tail] = value;
        size++;
        return this;
    }

    public ShortQueue addAllLast(short[] list){
        ensure(list.length);
        for(int i = 0;i < list.length;i++) addLast(list[i]);
        return this;
    }

    public ShortQueue removeFirst(){
        items[head] = NULL;
        head = increment(head);
        size--;
        return this;
    }

    public ShortQueue removeLast(){
        items[tail] = NULL;
        tail = decrement(tail);
        size--;
        return this;
    }

    public short popFirst(){
        short value = first();
        removeFirst();
        return value;
    }

    public short popLast(){
        short value = last();
        removeLast();
        return value;
    }


    public short get(int index){
        return items[trueIndex(index)];
    }

    public short first(){
        return items[head];
    }

    public short last(){
        return items[tail];
    }

    public int size(){
        return size;
    }

    public ShortQueue clear(){
        int n = min(items.length - head, size);
        for(int i = 0;i < n;i++) items[head + i] = NULL;
        for(int i = n;i < size;i++) items[i - n] = NULL;
        head = size = 0;
        tail = end;
        return this;
    }

    public ShortQueue ensure(int space){
        if(size + space >= items.length) resize(size + space + 1);
        return this;
    }

    public ShortQueue resize(int capacity){
        short[] old = items;
        items = new short[capacity];
        int n = min(items.length - head, size);
        for(int i = 0;i < n;i++) items[i] = old[head + i];
        for(int i = n;i < size;i++) items[i] = old[i - n];
        head = 0;
        tail = size - 1;
        end = capacity - 1;
        return this;
    }


    @Override
    public String toString(){
        Seq<Object> values = new Seq<>(size());
        for(int i = 0;i < size;i++) values.add(items[i]);
        return itrToString(values);
    }
}

