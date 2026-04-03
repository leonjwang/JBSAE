package jbsae.struct.prim.base;

import jbsae.struct.*;

import java.util.*;

import static jbsae.util.Stringf.*;

public class ShortSeq{
    private static final short NULL = 0;

    private short[] items;

    public boolean ordered = true;
    public int size;

    public ShortSeq(){
        this(16);
    }

    public ShortSeq(int capacity){
        items = new short[capacity];
    }


    public ShortSeq ordered(boolean ordered){
        this.ordered = ordered;
        return this;
    }

    public short get(int index){
        return items[index];
    }

    public ShortSeq set(int index, short value){
        this.items[index] = value;
        return this;
    }

    public ShortSeq set(short[] values){
        if(values.length >= items.length) resize(values.length + 1);
        for(int i = 0;i < values.length;i++) items[i] = values[i];
        for(int i = values.length;i < size;i++) items[i] = NULL;
        size = values.length;
        return this;
    }

    public ShortSeq add(short value){
        if(size >= items.length) resize((int)(items.length * 1.5f + 1));
        items[size++] = value;
        return this;
    }

    public ShortSeq add(int index, short value){
        if(size >= items.length) resize((int)(items.length * 1.5f + 1));
        for(int i = size++;i > index;i--) items[i] = items[i - 1];
        items[index] = value;
        return this;
    }

    public ShortSeq addAll(short[] values){
        ensure(values.length);
        for(int i = 0;i < values.length;i++) items[size + i] = values[i];
        size += values.length;
        return this;
    }

    public ShortSeq remove(int index){
        if(ordered) for(int i = index;i < --size;i++) items[i] = items[i + 1];
        else items[index] = items[--size];
        items[size] = NULL;
        return this;
    }


    public ShortSeq sort(){
        Arrays.sort(items, 0, size);
        return this;
    }

    public ShortSeq clear(){
        for(int i = 0;i < size;i++) items[i] = NULL;
        size = 0;
        return this;
    }

    public ShortSeq ensure(int space){
        if(size + space >= items.length) resize(size + space + 1);
        return this;
    }

    private void resize(int capacity){
        short[] old = this.items;
        this.items = new short[capacity];
        for(int i = 0;i < old.length;i++) items[i] = old[i];
    }

    public int size(){
        return size;
    }

    @Override
    public String toString(){
        Seq<Object> values = new Seq<>();
        for(int i = 0;i < size;i++) values.add(items[i]);
        return itrToString(values);
    }
}

