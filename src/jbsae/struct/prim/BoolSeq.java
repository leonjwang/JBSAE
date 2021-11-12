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
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public boolean[] list(){
        boolean[] values = new boolean[size];
        copy(items, values, size);
        return values;
    }

    public void add(boolean value){
        boolean[] items = this.items;
        if(size >= items.length) items = resize(max(8, size * 2));
        items[size++] = value;
    }

    public void add(boolean value, int index){
        boolean[] items = this.items;
        if(size >= items.length) items = resize(max(8, size * 2));
        shift(items, index, size++, 1);
        items[index] = value;
    }

    public void addAll(boolean... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public void remove(int index){
        boolean[] items = this.items;
        shift(items, index + 1, size--, -1);
        items[size] = false;
    }

    public void removeValue(boolean value){
        boolean[] items = this.items;
        for(int i = 0;i < size;i++){
            if(items[i] == value){
                remove(i);
                break;
            }
        }
    }

    public void remove(int... indexes){
        for(int i = 0;i < indexes.length;i++) remove(indexes[i]);
    }

    public void removeAll(boolean... values){
        boolean[] items = this.items;
        for(int i = 0;i < values.length;i++){
            for(int j = 0;j < size;j++){
                if(items[j] == values[i]) remove(j--);
            }
        }
    }

    public boolean get(int index){
        return items[index];
    }

    public boolean contains(boolean value){
        for(int i = 0;i < size;i++) if(items[i] == value) return true;
        return false;
    }

    public void trim(){
        resize(size);
    }

    public boolean[] resize(int newSize){
        boolean[] items = this.items;
        boolean[] newItems = new boolean[newSize];
        copy(items, newItems, size);
        this.items = newItems;
        return newItems;
    }
}