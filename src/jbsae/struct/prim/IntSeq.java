package jbsae.struct.prim;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class IntSeq{
    public int[] items;
    public int size;

    public IntSeq(){
        items = new int[4];
    }

    public IntSeq(int... values){
        this();
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public int[] list(){
        int[] values = new int[size];
        copy(items, values, size);
        return values;
    }

    public IntSeq set(IntSeq values){
        items = values.items;
        size = values.size;
        return this;
    }

    public IntSeq add(int value){
        int[] items = this.items;
        if(size >= items.length) items = resize(max(8, size * 2));
        items[size++] = value;
        return this;
    }

    public IntSeq add(int value, int index){
        int[] items = this.items;
        if(size >= items.length) items = resize(max(8, size * 2));
        shift(items, index, size++, 1);
        items[index] = value;
        return this;
    }

    public IntSeq addAll(int... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    public IntSeq remove(int index){
        int[] items = this.items;
        shift(items, index + 1, size--, -1);
        items[size] = 0;
        return this;
    }

    public IntSeq removeValue(int value){
        int[] items = this.items;
        for(int i = 0;i < size;i++){
            if(items[i] == value){
                remove(i);
                break;
            }
        }
        return this;
    }

    public IntSeq remove(int... indexes){
        for(int i = 0;i < indexes.length;i++) remove(indexes[i]);
        return this;
    }

    public IntSeq removeAll(int... values){
        int[] items = this.items;
        for(int i = 0;i < values.length;i++){
            for(int j = 0;j < size;j++) if(items[j] == values[i]) remove(j--);
        }
        return this;
    }

    public int get(int index){
        return items[index];
    }

    public boolean contains(int value){
        for(int i = 0;i < size;i++) if(items[i] == value) return true;
        return false;
    }

    public IntSeq sort(){
        trim();
        sortArr(items);
        return this;
    }

    public IntSeq clear(){
        items = new int[4];
        size = 0;
        return this;
    }

    public IntSeq trim(){
        resize(size);
        return this;
    }

    public int[] resize(int newSize){
        int[] items = this.items;
        int[] newItems = new int[newSize];
        copy(items, newItems, size);
        this.items = newItems;
        return newItems;
    }
}
