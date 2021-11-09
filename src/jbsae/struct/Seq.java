package jbsae.struct;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class Seq<T> implements Iterable<T>{
    public SeqIterator i1, i2;
    public T[] items;
    public int size;

    public Seq(){
        i1 = new SeqIterator();
        i2 = new SeqIterator();
        items = (T[])new Object[16];
    }

    public Seq(Object... values){
        this();
        for(Object t : values) add((T)t);
    }

    public void add(T value){
        T[] items = this.items;
        if(size == items.length) items = resize(max(8, (int)(size * 1.75f)));
        items[size++] = value;
    }

    public void remove(int index){
        T[] items = this.items;
        for(int i = index;i < size - 1;i++) items[i] = items[i + 1];
        items[size--] = null;
    }

    public void remove(T value){
        T[] items = this.items;
        for(int i = 0;i < size;i ++){
            if(eql(items[i], value)){
                remove(i);
                break;
            }
        }
    }

    public void removeAll(int... indexes){
        for(int i = 0;i < indexes.length;i++) remove(indexes[i]);
    }

    public T get(int index){
        return items[index];
    }

    public T[] resize(int newSize){
        T[] items = this.items;
        T[] newItems = create(newSize, items);
        for(int i = 0;i < Math.min(size, newItems.length);i++) newItems[i] = items[i];
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
        public int index = 0;

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

        @Override
        public void remove(){
            removeAll(--index);
        }
    }
}
