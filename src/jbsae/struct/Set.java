package jbsae.struct;

import jbsae.struct.Seq.*;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class Set<T> implements Iterable<T>{
    public SetIterator i1, i2;
    public T[] table;
    public int size = 0;

    public Set(){
        table = (T[])new Object[64];
        i1 = new SetIterator();
        i2 = new SetIterator();
    }

    public Set(Object... values){
        this();
        for(Object value : values) add((T)value);
    }

    public Object[] list(){
        Object[] values = create(size);
        int i = 0;
        for(T value : this) values[i++] = value;
        return values;
    }

    public void add(T value){
        T[] table = this.table;
        int h = value.hashCode();
        int[] checks = hashes(h, table.length);
        for(int i = 0;i < 3;i++) if(eql(table[checks[i]], value)) return;
        for(int i = 0;i < 3;i++){
            if(table[checks[i]] == null){
                table[checks[i]] = value;
                size++;
                return;
            }
        }
        resize(table.length << 1);
        add(value);
    }

    public void remove(T value){
        T[] table = this.table;
        int h = value.hashCode();
        int[] checks = hashes(h, table.length);
        for(int i = 0;i < 3;i++){
            if(eql(table[checks[i]], value)){
                table[checks[i]] = null;
                size--;
                return;
            }
        }
    }

    public void removeAll(T... values){
        for(T value : values) remove(value);
    }

    public boolean contains(T value){
        T[] table = this.table;
        int h = value.hashCode();
        int[] checks = hashes(h, table.length);
        for(int i = 0;i < 3;i++) if(eql(table[checks[i]], value)) return true;
        return false;
    }

    public void resize(int newSize){
        T[] values = create(size, table);
        int index = 0;
        for(int i = 0;i < table.length;i++) if(table[i] != null) values[index++] = table[i];
        size = 0;
        this.table = create(newSize);
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    @Override
    public Iterator<T> iterator(){
        if(i1.nextIndex >= table.length){
            i1.nextIndex = 0;
            return i1;
        }
        if(i2.nextIndex >= table.length){
            i2.nextIndex = 0;
            return i2;
        }
        return new SetIterator();
    }

    private class SetIterator implements Iterator<T>{
        public int nextIndex = -1;

        public SetIterator(){
        }

        public void findNextIndex(){
            for(nextIndex++;nextIndex < table.length;nextIndex++) if(table[nextIndex] != null) return;
        }

        @Override
        public boolean hasNext(){
            findNextIndex();
            return nextIndex < table.length;
        }

        @Override
        public T next(){
            return table[nextIndex];
        }

        @Override
        public void remove(){
            //TODO: remove
            //removeAll(table[nextIndex]);
        }
    }
}
