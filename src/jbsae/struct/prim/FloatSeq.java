package jbsae.struct.prim;

import jbsae.struct.*;
import jbsae.struct.prim.iterator.*;

import java.util.*;

import static jbsae.util.Stringf.*;

public class FloatSeq{
    private float[] items;

    public boolean ordered = true;
    public int size;

    public FloatSeq(){
        this(16);
    }

    public FloatSeq(int capacity){
        items = new float[capacity];
    }

    public FloatSeq ordered(boolean ordered){
        this.ordered = ordered;
        return this;
    }

    public float get(int index){
        return items[index];
    }

    public FloatSeq set(int index, float value){
        this.items[index] = value;
        return this;
    }

    public FloatSeq set(float[] values){
        ensure(values.length - size);
        System.arraycopy(values, 0, items, 0, values.length);
        size = values.length;
        return this;
    }

    public FloatSeq set(FloatIterator itr){
        if(itr instanceof Sized list) ensure(list.size() - size);
        clear();
        while(itr.hasNext()) add(itr.next());
        return this;
    }

    public FloatSeq add(float value){
        if(size >= items.length) resize(items.length + (items.length >> 1) + 1);
        items[size++] = value;
        return this;
    }

    public FloatSeq add(int index, float value){
        if(size >= items.length) resize(items.length + (items.length >> 1) + 1);
        System.arraycopy(items, index, items, index + 1, size - index);
        size++;
        items[index] = value;
        return this;
    }

    public FloatSeq addAll(FloatIterator itr){
        if(itr instanceof Sized list) ensure(list.size());
        while(itr.hasNext()) add(itr.next());
        return this;
    }

    public FloatSeq remove(int index){
        if(ordered) System.arraycopy(items, index + 1, items, index, --size - index);
        else items[index] = items[--size];
        return this;
    }

    public FloatSeq sort(){
        Arrays.sort(items, 0, size);
        return this;
    }

    public FloatSeq clear(){
        size = 0;
        return this;
    }

    public FloatSeq ensure(int space){
        if(size + space >= items.length) resize(size + space + 1);
        return this;
    }

    private void resize(int capacity){
        float[] old = this.items;
        this.items = new float[capacity];
        System.arraycopy(old, 0, this.items, 0, size);
    }

    public FloatIterator iterator(){
        return new SeqIterator();
    }

    @Override
    public String toString(){
        return itrToString(iterator());
    }

    private class SeqIterator extends FloatIterator implements Sized{
        public int index = 0;

        public SeqIterator(){
        }

        @Override
        public boolean hasNext(){
            return index < size;
        }

        @Override
        public float nextf(){
            return items[index++];
        }

        @Override
        public int size(){
            return size;
        }
    }
}