package jbsae.struct;

import jbsae.func.prim.*;

import java.util.*;

import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;


// TODO: I haven't decided whether I want to use Arrays.fill / System.arraycopy whenever possible or just let the JIT handle it.
public class Seq<T> implements Iterable<T>{
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

    public T get(int index){
        return items[index];
    }

    public Seq<T> set(int index, T value){
        this.items[index] = value;
        return this;
    }

    public Seq<T> set(T[] values){
        ensure(values.length - size);
        for(int i = 0;i < values.length;i++) items[i] = values[i];
        for(int i = values.length;i < size;i++) items[i] = null;
        size = values.length;
        return this;
    }

    public Seq<T> set(Iterator<T> itr) {
        if(itr instanceof Sized list) ensure(list.size() - size);
        int oldSize = size;
        clear();
        while(itr.hasNext()) add(itr.next());
        for(int i = size;i < oldSize;i++) items[i] = null;
        return this;
    }

    public Seq<T> set(Iterable<T> values){
        return set(values.iterator());
    }

    public Seq<T> add(T value){
        if(size >= items.length) resize((int)(items.length * 1.5f + 1));
        items[size++] = value;
        return this;
    }

    public Seq<T> add(int index, T value){
        index = Math.min(index, size);
        if(size >= items.length) resize((int)(items.length * 1.5f + 1));
        for(int i = size++;i > index;i--) items[i] = items[i - 1];
        items[index] = value;
        return this;
    }

    public Seq<T> addAll(Iterator<T> itr) {
        if(itr instanceof Sized list) ensure(list.size());
        while(itr.hasNext()) add(itr.next());
        return this;
    }

    public Seq<T> addAll(Iterable<T> values){
        return addAll(values.iterator());
    }

    public Seq<T> remove(int index){
        if(ordered) for(int i = index;i < --size;i++) items[i] = items[i + 1];
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
        Arrays.fill(items, 0, size, null);
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
        System.arraycopy(old, 0, this.items, 0, old.length);
    }

    @Override
    public Iterator<T> iterator(){
        return new SeqIterator();
    }

    @Override
    public String toString(){
        return itrToString(this);
    }

    private class SeqIterator implements Iterator<T>, Sized{
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
        public int size(){
            return size;
        }
    }
}
