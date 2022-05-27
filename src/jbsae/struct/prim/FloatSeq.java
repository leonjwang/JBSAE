package jbsae.struct.prim;

import jbsae.func.prim.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class FloatSeq{
    public float[] items;
    public int size;

    public FloatSeq(){
        items = new float[4];
    }

    public FloatSeq(float... values){
        this();
        set(values);
    }

    public float[] list(){
        float[] values = new float[size];
        copy(items, values, size);
        return values;
    }

    public FloatSeq set(float value, int index){
        items[index] = value;
        return this;
    }

    public FloatSeq set(float... values){
        clear();
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    public FloatSeq set(FloatSeq values){
        items = values.items;
        size = values.size;
        return this;
    }

    public FloatSeq add(float value){
        if(size >= items.length) resize(max(8, size * 2));
        items[size++] = value;
        return this;
    }

    public FloatSeq add(float value, int index){
        if(size >= items.length) resize(max(8, size * 2));
        shift(items, index, size++, 1);
        items[index] = value;
        return this;
    }

    public FloatSeq addAll(float... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    public FloatSeq remove(int index){
        shift(items, index + 1, size--, -1);
        items[size] = 0;
        return this;
    }

    public FloatSeq removeValue(float value){
        for(int i = 0;i < size;i++){
            if(items[i] == value){
                remove(i);
                break;
            }
        }
        return this;
    }

    public FloatSeq remove(int... indexes){
        for(int i = 0;i < indexes.length;i++) remove(indexes[i]);
        return this;
    }

    public FloatSeq removeAll(float... values){
        for(int i = 0;i < values.length;i++){
            for(int j = 0;j < size;j++) if(items[j] == values[i]) remove(j--);
        }
        return this;
    }

    public float get(int index){
        return items[index];
    }

    public boolean contains(float value){
        for(int i = 0;i < size;i++) if(eqlf(items[i], value)) return true;
        return false;
    }

    public boolean contains(Boolff condition){
        for(int i = 0;i < size;i++) if(condition.get(items[i])) return true;
        return false;
    }

    public FloatSeq each(Floatc cons){
        for(int i = 0;i < size;i++) cons.get(items[i]);
        return this;
    }

    public FloatSeq sort(){
        trim();
        sortArr(items);
        return this;
    }

    public FloatSeq clear(){
        fill(items, 0);
        size = 0;
        return this;
    }

    public FloatSeq trim(){
        resize(size);
        return this;
    }

    public FloatSeq resize(int newSize){
        float[] items = new float[newSize];
        copy(this.items, items, size);
        this.items = items;
        return this;
    }
}
