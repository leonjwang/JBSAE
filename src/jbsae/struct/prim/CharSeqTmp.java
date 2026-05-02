package jbsae.struct.prim;

import jbsae.struct.*;
import jbsae.struct.prim.iterator.*;

import java.util.*;

import static jbsae.util.Stringf.*;

// TODO: Finish + I just realized we don't actually need to set things to null like in Seq<T> because primitives aren't garbage collected the same way (it's fine to leave old values in)
public class CharSeqTmp{
    private char[] items;

    public int size;

    public CharSeqTmp(){
        this(16);
    }

    public CharSeqTmp(int capacity){
        items = new char[capacity];
    }


    public char charAt(int index){
        return items[index];
    }

    public CharSeqTmp set(int index, char value){
        this.items[index] = value;
        return this;
    }

    public CharSeqTmp set(char[] values){
        ensure(values.length - size);
        for(int i = 0;i < values.length;i++) items[i] = values[i];
        for(int i = values.length;i < size;i++) items[i] = '\0';
        size = values.length;
        return this;
    }

    public CharSeqTmp set(CharSeqTmp values){
        items = values.items;
        size = values.size;
        return this;
    }



    public CharSeqTmp lower(){
        for(int i = 0;i < size;i++) items[i] = lowerCase((char)items[i]);
        return this;
    }

    public CharSeqTmp upper(){
        for(int i = 0;i < size;i++) items[i] = upperCase((char)items[i]);
        return this;
    }


//    public jbsae.struct.Seq<T> add(T value){
//        if(size >= items.length) resize((int)(items.length * 1.5f + 1));
//        items[size++] = value;
//        return this;
//    }

//    public jbsae.struct.Seq<T> add(int index, T value){
//        index = Math.min(index, size);
//        if(size >= items.length) resize((int)(items.length * 1.5f + 1));
//        for(int i = size++;i > index;i--) items[i] = items[i - 1];
//        items[index] = value;
//        return this;
//    }

//    public jbsae.struct.Seq<T> addAll(Iterator<T> itr) {
//        if(itr instanceof Sized list) ensure(list.size());
//        while(itr.hasNext()) add(itr.next());
//        return this;
//    }

//    public jbsae.struct.Seq<T> addAll(Iterable<T> values){
//        return addAll(values.iterator());
//    }
//
//    public jbsae.struct.Seq<T> remove(int index){
//        if(ordered) for(int i = index;i < --size;i++) items[i] = items[i + 1];
//        else items[index] = items[--size];
//        items[size] = null;
//        return this;
//    }

//    public jbsae.struct.Seq<T> remove(T value){
//        for(int i = 0;i < size;i++) if(value.equals(items[i])) return remove(i);
//        return this;
//    }

    public CharSeqTmp clear(){
        Arrays.fill(items, 0, size, '\0');
        size = 0;
        return this;
    }

    public CharSeqTmp ensure(int space){
        if(size + space >= items.length) resize(size + space + 1);
        return this;
    }

    private void resize(int capacity){
        char[] old = this.items;
        this.items = new char[capacity];
        System.arraycopy(old, 0, this.items, 0, old.length);
    }

    public CharIterator iterator(){
        return new CharSeqIterator();
    }

    @Override
    public String toString(){
        char[] chars = new char[size];
        for(int i = 0;i < size;i++) chars[i] = (char)items[i];
        return combine(chars);
    }

    private class CharSeqIterator extends CharIterator implements Sized{
        @Override
        public boolean hasNext(){
            return index < size;
        }

        @Override
        public char next(){
            return items[index++];
        }

        @Override
        public int size(){
            return size;
        }
    }
}
