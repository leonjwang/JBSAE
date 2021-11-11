package jbsae.struct.prim;

import static jbsae.util.Mathf.*;

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
        for(int i = 0;i < items.length;i++) values[i] = items[i];
        return values;
    }

    public void add(int value){
        int[] items = this.items;
        if(size >= items.length) items = resize(max(8, size * 2));
        items[size++] = value;
    }

    public void add(int value, int index){
        int[] items = this.items;
        if(size >= items.length) items = resize(max(8, size * 2));
        for(int i = (size++) - 1;i >= index;i--) items[i + 1] = items[i];
        items[index] = value;
    }

    public void addAll(int... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public void remove(int index){
        int[] items = this.items;
        for(int i = index;i < size - 1;i++) items[i] = items[i + 1];
        if(size-- != items.length) items[size + 1] = 0;
    }

    public void removeValue(int value){
        int[] items = this.items;
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

    public void removeAll(int... values){
        int[] items = this.items;
        for(int i = 0;i < values.length;i++){
            for(int j = 0;j < size;j++){
                if(items[j] == values[i]) remove(j--);
            }
        }
    }

    public int get(int index){
        return items[index];
    }

    public boolean contains(int value){
        for(int i = 0;i < size;i++) if(items[i] == value) return true;
        return false;
    }

    public void trim(){
        resize(size);
    }

    public int[] resize(int newSize){
        int[] items = this.items;
        int[] newItems = new int[newSize];
        for(int i = 0;i < min(size, newSize);i++) newItems[i] = items[i];
        this.items = newItems;
        return newItems;
    }
}
