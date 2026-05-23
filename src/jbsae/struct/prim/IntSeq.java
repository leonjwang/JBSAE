package jbsae.struct.prim;

import jbsae.struct.*;
import jbsae.struct.prim.iterator.*;

import java.util.*;

import static jbsae.util.Stringf.*;

public class IntSeq{
    private int[] items;

    public boolean ordered = true;
    public int size;

    public IntSeq(){
        this(16);
    }

    public IntSeq(int capacity){
        items = new int[capacity];
    }

    public IntSeq ordered(boolean ordered){
        this.ordered = ordered;
        return this;
    }

    public int get(int index){
        return items[index];
    }

    public IntSeq set(int index, int value){
        this.items[index] = value;
        return this;
    }

    public IntSeq set(int[] values){
        ensure(values.length - size);
        System.arraycopy(values, 0, items, 0, values.length);
        size = values.length;
        return this;
    }

    public IntSeq set(IntIterator itr){
        if(itr instanceof Sized list) ensure(list.size() - size);
        clear();
        while(itr.hasNext()) add(itr.next());
        return this;
    }

    public IntSeq add(int value){
        if(size >= items.length) resize(items.length + (items.length >> 1) + 1);
        items[size++] = value;
        return this;
    }

    public IntSeq add(int index, int value){
        if(size >= items.length) resize(items.length + (items.length >> 1) + 1);
        System.arraycopy(items, index, items, index + 1, size - index);
        size++;
        items[index] = value;
        return this;
    }

    public IntSeq addAll(IntIterator itr){
        if(itr instanceof Sized list) ensure(list.size());
        while(itr.hasNext()) add(itr.next());
        return this;
    }

    public IntSeq remove(int index){
        if(ordered) System.arraycopy(items, index + 1, items, index, --size - index);
        else items[index] = items[--size];
        return this;
    }

    public IntSeq sort(){
        Arrays.sort(items, 0, size);
        return this;
    }

    public IntSeq clear(){
        size = 0;
        return this;
    }

    public IntSeq ensure(int space){
        if(size + space >= items.length) resize(size + space + 1);
        return this;
    }

    private void resize(int capacity){
        int[] old = this.items;
        this.items = new int[capacity];
        System.arraycopy(old, 0, this.items, 0, size);
    }

    public IntIterator iterator(){
        return new SeqIterator();
    }

    @Override
    public String toString(){
        return itrToString(iterator());
    }

    private class SeqIterator extends IntIterator implements Sized{
        public int index = 0;

        public SeqIterator(){
        }

        @Override
        public boolean hasNext(){
            return index < size;
        }

        @Override
        public int nexti(){
            return items[index++];
        }

        @Override
        public int size(){
            return size;
        }
    }
}