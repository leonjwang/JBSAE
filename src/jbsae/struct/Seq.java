package jbsae.struct;

import jbsae.func.prim.*;

import java.util.*;

import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;

public class Seq<T> implements List<T>{
    private T[] items;

    public boolean ordered = true;
    public int size;

    public Seq(){
        this(16);
    }

    public Seq(int capacity){
        items = create(capacity);
    }


    public Seq<T> ordered(boolean ordered){
        this.ordered = ordered;
        return this;
    }

    @Override
    public Object[] list(){
        Object[] values = new Object[size];
        copy(items, values, size);
        return values;
    }

    @Override
    public T get(int index){
        return items[index];
    }

    @Override
    public Seq<T> set(int index, T value){
        this.items[index] = value;
        return this;
    }

    public Seq<T> set(T[] values){
        if(values.length >= items.length) resize(values.length + 1);
        for(int i = 0;i < values.length;i++) items[i] = values[i];
        for(int i = values.length;i < size;i++) items[i] = null;
        size = values.length;
        return this;
    }

    public Seq<T> set(List<T> values){
        if(values.size() >= items.length) resize(values.size() + 1);
        for(int i = 0;i < values.size();i++) items[i] = values.get(i);
        for(int i = values.size();i < size;i++) items[i] = null;
        size = values.size();
        return this;
    }

    public Seq<T> add(T value){
        if(value == null) throw new RuntimeException("Value is null");
        if(size >= items.length) resize((int)(items.length * 1.5f + 1));
        items[size++] = value;
        return this;
    }

    public Seq<T> add(int index, T value){
        if(value == null) throw new RuntimeException("Value is null");
        if(size >= items.length) resize((int)(items.length * 1.5f + 1));
        shift(items, index, size++, 1);
        items[index] = value;
        return this;
    }

    public Seq<T> addAll(Object[] values){
        ensure(values.length);
        copy(values, 0, items, size, values.length);
        size += values.length;
        return this;
    }

    public Seq<T> addAll(List<T> values){
        ensure(values.size());
        for(int i = 0;i < values.size();i++) items[size++] = values.get(i);
        return this;
    }

    public Seq<T> remove(int index){
        if(ordered) shift(items, index + 1, size--, -1);
        else items[index] = items[--size];
        items[size] = null;
        return this;
    }

    public Seq<T> remove(T value){
        for(int i = 0;i < size;i++) if(value.equals(items[i])) return remove(i);
        return this;
    }


    public Seq<T> sort(Floatf<T> value){
        sortArr(items, 0, size, value);
        return this;
    }

    public Seq<T> clear(){
        fill(items, 0, size, null);
        size = 0;
        return this;
    }

    public Seq<T> ensure(int space){
        if(size + space >= items.length) resize(size + space + 1);
        return this;
    }

    private void resize(int capacity){
        T[] old = this.items;
        this.items = create(capacity);
        copy(old, this.items, size);
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public Iterator<T> iterator(){
        return new SeqIterator();
    }

    @Override
    public String toString(){
        return itrToString(this);
    }

    private class SeqIterator implements Iterator<T>{
        public int index;

        public SeqIterator(){
        }

        @Override
        public boolean hasNext(){
            return index < size;
        }

        @Override
        public T next(){
            return items[index++];
        }
    }
}
