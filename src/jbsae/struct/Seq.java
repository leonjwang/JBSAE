package jbsae.struct;

import jbsae.func.*;
import jbsae.func.prim.*;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;


public class Seq<T> implements List<T>{
    public T[] items;
    public int size;


    public Seq(){
        this(4);
    }

    public Seq(int size){
        items = (T[])new Object[size];
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
        return Arrays.copyOf(items, size);
    }


    @Override
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

    public Seq<T> add(T value){
        if(value == null) throw new RuntimeException("Value is null");
        if(size >= items.length) resize(max(8, size * 2));
        items[size++] = value;
        return this;
    }

    public Seq<T> add(T value, int index){
        if(value == null) throw new RuntimeException("Value is null");
        if(size >= items.length) resize(max(8, size * 2));
        shift(items, index, size++, 1);
        items[index] = value;
        return this;
    }

    public Seq<T> addAll(T... values){
        ensureCap(values.length);
        copy(values, 0, values.length, items, size);
        size += values.length;
        return this;
    }

    public Seq<T> addAll(List<T> values){
        ensureCap(values.size());
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

    public Seq<T> ensureCap(int space){
        if(size + space >= items.length) resize(max(size + space, items.length * 2));
        return this;
    }


    @Override
    public T get(int index){
        return items[index];
    }

    @Override
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
        sortArr(items, 0, size);
        return this;
    }

    public Seq<T> sort(Floatf<T> value){
        sortArr(items, 0, size, value);
        return this;
    }

    public Seq<T> clear(){
        fill(items, null, 0, size);
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
