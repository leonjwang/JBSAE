package jbsae.struct.prim;

import jbsae.func.prim.*;

import static jbsae.util.Mathf.*;

public class BoolQueue{
    public boolean[] items;
    public int head, tail, size;

    public BoolQueue(){
        items = new boolean[4];
    }

    public BoolQueue(boolean... values){
        this();
        for(int i = 0;i < values.length;i++) addLast(values[i]);
    }

    public boolean[] list(){
        boolean[] values = new boolean[size];
        for(int i = 0;i < size;i++) values[i] = get(i);
        return values;
    }

    public BoolQueue addFirst(boolean value){
        boolean[] items = this.items;
        if(size == items.length) items = resize(max(8, size * 2));
        head = mod(head - 1, items.length);
        items[head] = value;
        size++;
        return this;
    }

    public BoolQueue addLast(boolean value){
        boolean[] items = this.items;
        if(size == items.length) items = resize(max(8, size * 2));
        items[tail] = value;
        tail = mod(tail + 1, items.length);
        size++;
        return this;
    }

    public BoolQueue removeFirst(){
        boolean[] items = this.items;
        items[head] = false;
        head = mod(head + 1, items.length);
        size--;
        return this;
    }

    public BoolQueue removeLast(){
        boolean[] items = this.items;
        tail = mod(tail - 1, items.length);
        items[tail] = false;
        size--;
        return this;
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
        items = new boolean[4];
        head = tail = size = 0;
        return this;
    }

    public BoolQueue trim(){
        resize(size);
        return this;
    }

    public boolean[] resize(int newSize){
        boolean[] items = this.items;
        boolean[] newItems = new boolean[newSize];
        for(int i = 0;i < size;i++) newItems[i] = get(i);
        this.items = newItems;
        head = 0;
        tail = size;
        return newItems;
    }
}
