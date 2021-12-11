package jbsae.struct.prim;

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
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public float[] list(){
        float[] values = new float[size];
        copy(items, values, size);
        return values;
    }

    public FloatSeq set(FloatSeq values){
        items = values.items;
        size = values.size;
        return this;
    }

    public FloatSeq add(float value){
        float[] items = this.items;
        if(size >= items.length) items = resize(max(8, size * 2));
        items[size++] = value;
        return this;
    }

    public FloatSeq add(float value, int index){
        float[] items = this.items;
        if(size >= items.length) items = resize(max(8, size * 2));
        shift(items, index, size++, 1);
        items[index] = value;
        return this;
    }

    public FloatSeq addAll(float... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    public FloatSeq remove(int index){
        float[] items = this.items;
        shift(items, index + 1, size--, -1);
        items[size] = 0;
        return this;
    }

    public FloatSeq removeValue(float value){
        float[] items = this.items;
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
        float[] items = this.items;
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

    public FloatSeq sort(){
        trim();
        sortArr(items);
        return this;
    }

    public FloatSeq clear(){
        items = new float[4];
        size = 0;
        return this;
    }

    public FloatSeq trim(){
        resize(size);
        return this;
    }

    public float[] resize(int newSize){
        float[] items = this.items;
        float[] newItems = new float[newSize];
        copy(items, newItems, size);
        this.items = newItems;
        return newItems;
    }
}
