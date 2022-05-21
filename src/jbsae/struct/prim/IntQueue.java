package jbsae.struct.prim;

import jbsae.func.prim.*;

import static jbsae.util.Mathf.*;

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
        int[] items = this.items;
        if(size == items.length) items = resize(max(8, size * 2));
        head = mod(head - 1, items.length);
        items[head] = value;
        size++;
        return this;
    }

    public IntQueue addLast(int value){
        int[] items = this.items;
        if(size == items.length) items = resize(max(8, size * 2));
        items[tail] = value;
        tail = mod(tail + 1, items.length);
        size++;
        return this;
    }

    public IntQueue removeFirst(){
        int[] items = this.items;
        items[head] = 0;
        head = mod(head + 1, items.length);
        size--;
        return this;
    }

    public IntQueue removeLast(){
        int[] items = this.items;
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
        items = new int[4];
        head = tail = size = 0;
        return this;
    }

    public IntQueue trim(){
        resize(size);
        return this;
    }

    public int[] resize(int newSize){
        int[] items = this.items;
        int[] newItems = new int[newSize];
        for(int i = 0;i < size;i++) newItems[i] = get(i);
        this.items = newItems;
        head = 0;
        tail = size;
        return newItems;
    }
}
