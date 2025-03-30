package jbsae.struct.prim;

import jbsae.func.prim.*;
import jbsae.struct.Seq.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class BoolQueue{
    public boolean[] items;
    public int head, tail, size;

    public BoolQueue(){
        this(4);
    }

    public BoolQueue(int size){
        items = new boolean[size];
    }

    public BoolQueue(boolean... values){
        this(values.length);
        for(int i = 0;i < values.length;i++) addLast(values[i]);
    }

    public boolean[] list(){
        boolean[] values = new boolean[size];
        for(int i = 0;i < size;i++) values[i] = get(i);
        return values;
    }

    public BoolQueue addFirst(boolean value){
        if(size == items.length) resize(max(8, size * 2));
        head = mod(head - 1, items.length);
        items[head] = value;
        size++;
        return this;
    }

    public BoolQueue addLast(boolean value){
        if(size == items.length) resize(max(8, size * 2));
        items[tail] = value;
        tail = mod(tail + 1, items.length);
        size++;
        return this;
    }

    public BoolQueue removeFirst(){
        items[head] = false;
        head = mod(head + 1, items.length);
        size--;
        return this;
    }

    public BoolQueue removeLast(){
        tail = mod(tail - 1, items.length);
        items[tail] = false;
        size--;
        return this;
    }

    public boolean popFirst(){
        boolean value = first();
        removeFirst();
        return value;
    }

    public boolean popLast(){
        boolean value = last();
        removeLast();
        return value;
    }

    public boolean get(int index){
        return items[mod((head + index), items.length)];
    }

    public boolean first(){
        return get(0);
    }

    public boolean last(){
        return get(size - 1);
    }

    public BoolQueue each(Boolc cons){
        for(int i = 0;i < size;i++) cons.get(get(i));
        return this;
    }

    public BoolQueue clear(){
        fill(items, false);
        head = tail = size = 0;
        return this;
    }

    public BoolQueue trim(){
        resize(size);
        return this;
    }

    public BoolQueue resize(int newSize){
        boolean[] items = new boolean[newSize];
        for(int i = 0;i < size;i++) items[i] = get(i);
        this.items = items;
        head = 0;
        tail = size;
        return this;
    }
}
