package jbsae.struct.prim;

import jbsae.func.prim.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class BoolSeq{
    public boolean[] items;
    public int size;

    public BoolSeq(){
        items = new boolean[4];
    }

    public BoolSeq(boolean... values){
        this();
        set(values);
    }

    public boolean[] list(){
        boolean[] values = new boolean[size];
        copy(items, values, size);
        return values;
    }

    public BoolSeq set(boolean value, int index){
        items[index] = value;
        return this;
    }

    public BoolSeq set(boolean... values){
        clear();
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    public BoolSeq set(BoolSeq values){
        items = values.items;
        size = values.size;
        return this;
    }

    public BoolSeq add(boolean value){
        if(size >= items.length) resize(max(8, size * 2));
        items[size++] = value;
        return this;
    }

    public BoolSeq add(boolean value, int index){
        if(size >= items.length) resize(max(8, size * 2));
        shift(items, index, size++, 1);
        items[index] = value;
        return this;
    }

    public BoolSeq addAll(boolean... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    public BoolSeq remove(int index){
        shift(items, index + 1, size--, -1);
        items[size] = false;
        return this;
    }

    public BoolSeq removeValue(boolean value){
        for(int i = 0;i < size;i++){
            if(items[i] == value){
                remove(i);
                break;
            }
        }
        return this;
    }

    public BoolSeq remove(int... indexes){
        for(int i = 0;i < indexes.length;i++) remove(indexes[i]);
        return this;
    }

    public BoolSeq removeAll(boolean... values){
        for(int i = 0;i < values.length;i++){
            for(int j = 0;j < size;j++) if(items[j] == values[i]) remove(j--);
        }
        return this;
    }

    public boolean get(int index){
        return items[index];
    }

    public BoolSeq each(Boolc cons){
        for(int i = 0;i < size;i++) cons.get(items[i]);
        return this;
    }

    public BoolSeq clear(){
        fill(items, false);
        size = 0;
        return this;
    }

    public BoolSeq trim(){
        resize(size);
        return this;
    }

    public BoolSeq resize(int newSize){
        boolean[] items = new boolean[newSize];
        copy(this.items, items, size);
        this.items = items;
        return this;
    }
}