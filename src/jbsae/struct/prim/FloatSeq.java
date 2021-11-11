package jbsae.struct.prim;

import static jbsae.util.Mathf.*;

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
        for(int i = 0;i < items.length;i++) values[i] = items[i];
        return values;
    }

    public void add(float value){
        float[] items = this.items;
        if(size >= items.length) items = resize(max(8, size * 2));
        items[size++] = value;
    }

    public void add(float value, int index){
        float[] items = this.items;
        if(size >= items.length) items = resize(max(8, size * 2));
        for(int i = (size++) - 1;i >= index;i--) items[i + 1] = items[i];
        items[index] = value;
    }

    public void addAll(float... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public void remove(int index){
        float[] items = this.items;
        for(int i = index;i < size - 1;i++) items[i] = items[i + 1];
        if(size-- != items.length) items[size + 1] = 0f;
    }

    public void removeValue(float value){
        float[] items = this.items;
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

    public void removeAll(float... values){
        float[] items = this.items;
        for(int i = 0;i < values.length;i++){
            for(int j = 0;j < size;j++){
                if(items[j] == values[i]) remove(j--);
            }
        }
    }

    public float get(int index){
        return items[index];
    }

    public boolean contains(float value){
        for(int i = 0;i < size;i++) if(eqlf(items[i], value)) return true;
        return false;
    }

    public void trim(){
        resize(size);
    }

    public float[] resize(int newSize){
        float[] items = this.items;
        float[] newItems = new float[newSize];
        for(int i = 0;i < min(size, newSize);i++) newItems[i] = items[i];
        this.items = newItems;
        return newItems;
    }
}
