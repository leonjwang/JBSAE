package jbsae.struct.prim;

import jbsae.func.prim.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class IntQueue{
    public int[] items;
    public int head, tail, size;

    public IntQueue(){
        items = new int[4];
    }

    public IntQueue(int... values){
        this();
        for(int i = 0;i < values.length;i++) addLast(values[i]);
    }

    public int[] list(){
        int[] values = new int[size];
        for(int i = 0;i < size;i++) values[i] = get(i);
        return values;
    }

    public IntQueue addFirst(int value){
        if(size == items.length) resize(max(8, size * 2));
        head = mod(head - 1, items.length);
        items[head] = value;
        size++;
        return this;
    }

    public IntQueue addLast(int value){
        if(size == items.length) resize(max(8, size * 2));
        items[tail] = value;
        tail = mod(tail + 1, items.length);
        size++;
        return this;
    }

    public IntQueue removeFirst(){
        items[head] = 0;
        head = mod(head + 1, items.length);
        size--;
        return this;
    }

    public IntQueue removeLast(){
        tail = mod(tail - 1, items.length);
        items[tail] = 0;
        size--;
        return this;
    }

    public int get(int index){
        return items[mod((head + index), items.length)];
    }

    public int first(){
        return get(0);
    }

    public int last(){
        return get(size - 1);
    }

    public boolean contains(int value){
        for(int i = 0;i < size;i++) if(get(i) == value) return true;
        return false;
    }

    public boolean contains(Boolfi condition){
        for(int i = 0;i < size;i++) if(condition.get(get(i))) return true;
        return false;
    }

    public IntQueue each(Intc cons){
        for(int i = 0;i < size;i++) cons.get(get(i));
        return this;
    }

    public IntQueue clear(){
        fill(items, 0);
        head = tail = size = 0;
        return this;
    }

    public IntQueue trim(){
        resize(size);
        return this;
    }

    public IntQueue resize(int newSize){
        int[] items = new int[newSize];
        for(int i = 0;i < size;i++) items[i] = get(i);
        this.items = items;
        head = 0;
        tail = size;
        return this;
    }
}
