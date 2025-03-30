package jbsae.struct.prim;

import jbsae.func.prim.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class IntSeq{
    public int[] items;
    public int size;

    public IntSeq(){
        this(4);
    }

    public IntSeq(int size){
        items = new int[size];
    }

    public IntSeq(int... values){
        this(values.length);
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public int[] list(){
        int[] values = new int[size];
        copy(items, values, size);
        return values;
    }

    public IntSeq set(int value, int index){
        items[index] = value;
        return this;
    }

    public IntSeq set(int... values){
        clear();
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    public IntSeq set(IntSeq values){
        items = values.items;
        size = values.size;
        return this;
    }

    public IntSeq add(int value){
        if(size >= items.length) resize(max(8, size * 2));
        items[size++] = value;
        return this;
    }

    public IntSeq add(int value, int index){
        if(size >= items.length) resize(max(8, size * 2));
        shift(items, index, size++, 1);
        items[index] = value;
        return this;
    }

    public IntSeq addAll(int... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    public IntSeq remove(int index){
        shift(items, index + 1, size--, -1);
        items[size] = 0;
        return this;
    }

    public IntSeq removeValue(int value){
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

    public boolean contains(Boolfi condition){
        for(int i = 0;i < size;i++) if(condition.get(items[i])) return true;
        return false;
    }

    public IntSeq each(Intc cons){
        for(int i = 0;i < size;i++) cons.get(items[i]);
        return this;
    }

    public IntSeq sort(){
        trim();
        sortArr(items);
        return this;
    }

    public IntSeq clear(){
        fill(items, 0);
        size = 0;
        return this;
    }

    public IntSeq trim(){
        resize(size);
        return this;
    }

    public IntSeq resize(int newSize){
        int[] items = new int[newSize];
        copy(this.items, items, size);
        this.items = items;
        return this;
    }
}
