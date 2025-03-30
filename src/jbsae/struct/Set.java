package jbsae.struct;

import jbsae.*;
import jbsae.func.*;
import jbsae.struct.Queue.*;
import jbsae.struct.Seq.*;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;

public class Set<T> implements Iterable<T>{
    public SetIterator i1, i2;
    public T[] table;
    public int size = 0;


    public Set(){
        this(4);
    }

    public Set(int size){
        table = (T[])new Object[size];
        i1 = new SetIterator();
        i2 = new SetIterator();
    }

    public Set(Object... values){
        this(values.length);
        for(Object value : values) add((T)value);
    }

    public Set(Iterable<T> values){
        this();
        for(T value : values) add(value);
    }


    public Object[] list(){
        int i = 0;
        Object[] values = create(size);
        for(T value : this) values[i++] = value;
        return values;
    }


    public Set<T> add(T value){
        int[] checks = hash3(value.hashCode(), table.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(table[checks[i]], value)) return this;
        for(int i = 0;i < checks.length;i++){
            if(table[checks[i]] == null){
                table[checks[i]] = value;
                size++;
                return this;
            }
        }
        resize(table.length << 1);
        add(value);
        return this;
    }

    public Set<T> addAll(T... values){
        for(T value : values) add(value);
        return this;
    }

    public Set<T> remove(T value){
        int[] checks = hash3(value.hashCode(), table.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(eql(table[checks[i]], value)){
                table[checks[i]] = null;
                size--;
                return this;
            }
        }
        return this;
    }

    public Set<T> removeAll(T... values){
        for(T value : values) remove(value);
        return this;
    }


    public boolean contains(T value){
        int[] checks = hash3(value.hashCode(), table.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(table[checks[i]], value)) return true;
        return false;
    }

    public Set<T> each(Cons<T> cons){
        for(T t : this) cons.get(t);
        return this;
    }

    public Set<T> clear(){
        fill(table, null);
        size = 0;
        return this;
    }

    public Set<T> resize(int newSize){
        T[] values = (T[])list();
        size = 0;
        this.table = create(newSize, table);
        for(int j = 0;j < values.length;j++) add(values[j]);
        return this;
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

    @Override
    public String toString(){
        return itrToString(this);
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
    }
}
