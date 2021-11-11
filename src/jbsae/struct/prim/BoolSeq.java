package jbsae.struct.prim;

import static jbsae.util.Mathf.*;

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
        for(int i = 0;i < items.length;i++) values[i] = items[i];
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
        for(int i = (size++) - 1;i >= index;i--) items[i + 1] = items[i];
        items[index] = value;
    }

    public void addAll(boolean... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public void remove(int index){
        boolean[] items = this.items;
        for(int i = index;i < size - 1;i++) items[i] = items[i + 1];
        if(size-- != items.length) items[size + 1] = false;
    }

    public void remove(int... indexes){
        for(int i = 0;i < indexes.length;i++) remove(indexes[i]);
    }

    public boolean get(int index){
        return items[index];
    }

    public void trim(){
        resize(size);
    }

    public boolean[] resize(int newSize){
        boolean[] items = this.items;
        boolean[] newItems = new boolean[newSize];
        for(int i = 0;i < min(size, newSize);i++) newItems[i] = items[i];
        this.items = newItems;
        return newItems;
    }
}