package jbsae.struct;

import jbsae.func.prim.*;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

/** @author Nathan Sweet */
public class Seq<T> implements Iterable<T>{
    public SeqIterator i1, i2;
    public T[] items;
    public int size;

    public Seq(){
        items = (T[])new Object[4];
        i1 = new SeqIterator();
        i2 = new SeqIterator();
    }

    public Seq(Object... values){
        this();
        for(Object value : values) add((T)value);
    }

    public Object[] list(){
        int i = 0;
        Object[] values = create(size);
        for(T value : this) values[i++] = value;
        return values;
    }

    public Seq<T> set(Seq<T> values){
        items = values.items;
        size = values.size;
        return this;
    }

    public Seq<T> add(T value){
        T[] items = this.items;
        if(size >= items.length) items = resize(max(8, size * 2));
        items[size++] = value;
        return this;
    }

    public Seq<T> add(T value, int index){
        T[] items = this.items;
        if(size >= items.length) items = resize(max(8, size * 2));
        shift(items, index, size++, 1);
        items[index] = value;
        return this;
    }

    public Seq<T> addAll(T... values){
        for(T value : values) add(value);
        return this;
    }

    public Seq<T> addAll(Seq<T> values){
        for(T value : values) add(value);
        return this;
    }

    public Seq<T> remove(int index){
        T[] items = this.items;
        shift(items, index + 1, size--, -1);
        items[size] = null;
        return this;
    }

    public Seq<T> remove(T value){
        T[] items = this.items;
        for(int i = 0;i < size;i++){
            if(eql(items[i], value)){
                remove(i);
                break;
            }
        }
        return this;
    }

    public Seq<T> remove(int... indexes){
        for(int i = 0;i < indexes.length;i++) remove(indexes[i]);
        return this;
    }

    public Seq<T> removeAll(T... values){
        T[] items = this.items;
        for(T value : values){
            for(int i = 0;i < size;i++) if(eql(items[i], value)) remove(i--);
        }
        return this;
    }

    public T get(int index){
        return items[index];
    }

    public boolean contains(T value){
        for(int i = 0;i < size;i++) if(eql(items[i], value)) return true;
        return false;
    }

    public Seq<T> sort(){
        trim();
        sortArr(items);
        return this;
    }

    public Seq<T> sort(Floatf<T> value){
        trim();
        sortArr(items, value);
        return this;
    }

    public Seq<T> clear(){
        items = create(4, items);
        size = 0;
        return this;
    }

    public Seq<T> trim(){
        resize(size);
        return this;
    }

    public T[] resize(int newSize){
        T[] items = this.items;
        T[] newItems = create(newSize, items);
        copy(items, newItems, size);
        this.items = newItems;
        return newItems;
    }

    @Override
    public Iterator<T> iterator(){
        if(i1.index >= size){
            i1.index = 0;
            return i1;
        }
        if(i2.index >= size){
            i2.index = 0;
            return i2;
        }
        return new SeqIterator();
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
