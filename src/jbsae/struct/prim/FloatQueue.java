package jbsae.struct.prim;

import jbsae.func.prim.*;

import static jbsae.util.Mathf.*;

public class FloatQueue{
    public float[] items;
    public int head, tail, size;

    public FloatQueue(){
        items = new float[4];
    }

    public FloatQueue(float... values){
        this();
        for(int i = 0;i < values.length;i++) addLast(values[i]);
    }

    public float[] list(){
        float[] values = new float[size];
        for(int i = 0;i < size;i++) values[i] = get(i);
        return values;
    }

    public FloatQueue addFirst(float value){
        float[] items = this.items;
        if(size == items.length) items = resize(max(8, size * 2));
        head = mod(head - 1, items.length);
        items[head] = value;
        size++;
        return this;
    }

    public FloatQueue addLast(float value){
        float[] items = this.items;
        if(size == items.length) items = resize(max(8, size * 2));
        items[tail] = value;
        tail = mod(tail + 1, items.length);
        size++;
        return this;
    }

    public FloatQueue removeFirst(){
        float[] items = this.items;
        items[head] = 0;
        head = mod(head + 1, items.length);
        size--;
        return this;
    }

    public FloatQueue removeLast(){
        float[] items = this.items;
        tail = mod(tail - 1, items.length);
        items[tail] = 0;
        size--;
        return this;
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
        items = new float[4];
        head = tail = size = 0;
        return this;
    }

    public FloatQueue trim(){
        resize(size);
        return this;
    }

    public float[] resize(int newSize){
        float[] items = this.items;
        float[] newItems = new float[newSize];
        for(int i = 0;i < size;i++) newItems[i] = get(i);
        this.items = newItems;
        head = 0;
        tail = size;
        return newItems;
    }
}
