package jbsae.struct;

import jbsae.func.*;
import jbsae.func.prim.*;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;


public class Seq<T> implements List<T>{
    public SeqIterator i1, i2;
    public T[] items;
    public int size;


    public Seq(){
        this(4);
    }

    public Seq(int size){
        items = (T[])new Object[size];
        i1 = new SeqIterator();
        i2 = new SeqIterator();
    }

    public Seq(Object... values){
        this(values.length);
        set(values);
    }

    public Seq(Iterable<T> values){
        this();
        for(T value : values) add(value);
    }


    @Override
    public Object[] list(){
        int i = 0;
        Object[] values = create(size);
        for(T value : this) values[i++] = value;
        return values;
    }


    @Override //TODO: Consider changing to index, value
    public Seq<T> set(T value, int index){
        items[index] = value;
        return this;
    }

    @Override
    public Seq<T> set(Object... values){
        clear();
        for(Object value : values) add((T)value);
        return this;
    }

    @Override
    public List<T> set(List<T> values){
        clear();
        for(T value : values) add(value);
        return this;
    }

    /** WARNING: This only copies the array reference and not each value themselves. */
    public Seq<T> set(Seq<T> values){
        items = values.items;
        size = values.size;
        return this;
    }

    public Seq<T> add(T value){
        if(value == null) return this;
        if(size >= items.length) resize(max(8, size * 2));
        items[size++] = value;
        return this;
    }

    public Seq<T> add(T value, int index){
        if(value == null) return this;
        if(size >= items.length) resize(max(8, size * 2));
        shift(items, index, size++, 1);
        items[index] = value;
        return this;
    }

    public Seq<T> addAll(T... values){
        for(T value : values) add(value);
        return this;
    }

    public Seq<T> addAll(List<T> values){
        for(T value : values) add(value);
        return this;
    }

    public Seq<T> remove(int index){
        shift(items, index + 1, size--, -1);
        items[size] = null;
        return this;
    }

    public Seq<T> remove(T value){
        for(int i = 0;i < size;i++){
            if(eql(items[i], value)){
                remove(i);
                break;
            }
        }
        return this;
    }


    @Override
    public T get(int index){
        return items[index];
    }

    @Override //This sucks
    public int size(){
        return size;
    }


    public boolean contains(T value){
        for(int i = 0;i < size;i++) if(eql(items[i], value)) return true;
        return false;
    }

    public boolean contains(Boolf<T> condition){
        for(int i = 0;i < size;i++) if(condition.get(items[i])) return true;
        return false;
    }

    public Seq<T> each(Cons<T> cons){
        for(int i = 0;i < size;i++) cons.get(items[i]);
        return this;
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
        fill(items, null);
        size = 0;
        return this;
    }

    public Seq<T> trim(){
        resize(size);
        return this;
    }

    public Seq<T> resize(int newSize){
        T[] items = create(newSize, this.items);
        copy(this.items, items, size);
        this.items = items;
        return this;
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
