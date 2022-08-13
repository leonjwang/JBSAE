package jbsae.struct.prim;

import jbsae.func.prim.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class FloatQueue{
    public float[] items;
    public int head, tail, size;

    public FloatQueue(){
        this(4);
    }

    public FloatQueue(int size){
        items = new float[size];
    }

    public FloatQueue(float... values){
        this(values.length);
        for(int i = 0;i < values.length;i++) addLast(values[i]);
    }

    public float[] list(){
        float[] values = new float[size];
        for(int i = 0;i < size;i++) values[i] = get(i);
        return values;
    }

    public FloatQueue addFirst(float value){
        if(size == items.length) resize(max(8, size * 2));
        head = mod(head - 1, items.length);
        items[head] = value;
        size++;
        return this;
    }

    public FloatQueue addLast(float value){
        if(size == items.length) resize(max(8, size * 2));
        items[tail] = value;
        tail = mod(tail + 1, items.length);
        size++;
        return this;
    }

    public FloatQueue removeFirst(){
        items[head] = 0;
        head = mod(head + 1, items.length);
        size--;
        return this;
    }

    public FloatQueue removeLast(){
        tail = mod(tail - 1, items.length);
        items[tail] = 0;
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
        return items[mod((head + index), items.length)];
    }

    public float first(){
        return get(0);
    }

    public float last(){
        return get(size - 1);
    }

    public boolean contains(float value){
        for(int i = 0;i < size;i++) if(eqlf(get(i), value)) return true;
        return false;
    }

    public boolean contains(Boolff condition){
        for(int i = 0;i < size;i++) if(condition.get(get(i))) return true;
        return false;
    }

    public FloatQueue each(Floatc cons){
        for(int i = 0;i < size;i++) cons.get(get(i));
        return this;
    }

    public FloatQueue clear(){
        fill(items, 0);
        head = tail = size = 0;
        return this;
    }

    public FloatQueue trim(){
        resize(size);
        return this;
    }

    public FloatQueue resize(int newSize){
        float[] items = new float[newSize];
        for(int i = 0;i < size;i++) items[i] = get(i);
        this.items = items;
        head = 0;
        tail = size;
        return this;
    }
}
