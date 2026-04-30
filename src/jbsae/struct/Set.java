package jbsae.struct;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;

public class Set<T> implements Listable<T>{
    private T[] table;

    private int tableCap, stashCap;
    private int threshold, shift, mask, steps;

    private int stashSize;

    public float loadFactor = 0.8f;
    public int size;

    public Set(){
        this(50);
    }

    public Set(int capacity){
        setCap(nextPow2((int)(capacity / loadFactor + 1)));
        table = create(tableCap + stashCap);
    }

    public Set<T> loadFactor(float loadFactor){
        this.loadFactor = loadFactor;
        this.threshold = (int)(tableCap * loadFactor);
        return this;
    }


    private void setCap(int cap){
        this.tableCap = cap;
        this.stashCap = floorLog2(cap) * 2;
        this.threshold = (int)(cap * loadFactor);
        this.shift = floorLog2(cap);
        this.mask = cap - 1;
        this.steps = (int)(rt2(cap) / 16 + 4);
    }

    public Set<T> add(T value){
        if(value == null) throw new RuntimeException("Value is null");

        int base = value.hashCode();
        int hash1 = base & mask;
        int hash2 = hash(base, shift, PRIME1) & mask;
        int hash3 = hash(base, shift, PRIME2) & mask;
        int hash4 = hash(base, shift, PRIME3) & mask;

        if(value.equals(table[hash1])) return this;
        if(value.equals(table[hash2])) return this;
        if(value.equals(table[hash3])) return this;
        if(value.equals(table[hash4])) return this;

        if(tryPlace(hash1, value)) return this;
        if(tryPlace(hash2, value)) return this;
        if(tryPlace(hash3, value)) return this;
        if(tryPlace(hash4, value)) return this;

        place(swapRandom(hash1, hash2, hash3, hash4, value));

        return this;
    }

    public Set<T> addAll(Iterator<T> itr) {
        if(itr instanceof Listerator<T> list) ensure(list.size());
        while(itr.hasNext()) add(itr.next());
        return this;
    }

    public Set<T> addAll(Iterable<T> values){
        return addAll(values.iterator());
    }

    public boolean contains(T value){
        if(value == null) throw new RuntimeException("Value is null");
        int base = value.hashCode();
        if(value.equals(table[base & mask])) return true;
        if(value.equals(table[hash(base, shift, PRIME1) & mask])) return true;
        if(value.equals(table[hash(base, shift, PRIME2) & mask])) return true;
        if(value.equals(table[hash(base, shift, PRIME3) & mask])) return true;
        for(int i = 0;i < stashSize;i++) if(value.equals(table[tableCap + i])) return true;
        return false;
    }

    public Set<T> remove(T value){
        if(value == null) throw new RuntimeException("Value is null");
        int base = value.hashCode();
        if(tryErase(base & mask, value)) return this;
        if(tryErase(hash(base, shift, PRIME1) & mask, value)) return this;
        if(tryErase(hash(base, shift, PRIME2) & mask, value)) return this;
        if(tryErase(hash(base, shift, PRIME3) & mask, value)) return this;
        for(int i = 0;i < stashSize;i++) if(tryErase(tableCap + i, value)) return this;
        return this;
    }

    private boolean tryErase(int index, T value){
        if(!value.equals(table[index])) return false;
        table[index] = null;
        size--;
        return true;
    }

    public Set<T> ensure(int space){
        int needed = (int)((size + space) / loadFactor + 1);
        if((size + needed) > tableCap) resize(nextPow2((size + needed)));
        return this;
    }

    private void place(T value){
        for(int i = 0;i < steps;i++){
            int base = value.hashCode();

            int hash1 = base & mask;
            if(tryPlace(hash1, value)) return;

            int hash2 = hash(base, shift, PRIME1) & mask;
            if(tryPlace(hash2, value)) return;

            int hash3 = hash(base, shift, PRIME2) & mask;
            if(tryPlace(hash3, value)) return;

            int hash4 = hash(base, shift, PRIME3) & mask;
            if(tryPlace(hash4, value)) return;

            value = swapRandom(hash1, hash2, hash3, hash4, value);
        }

        stash(value);
    }

    private boolean tryPlace(int index, T value){
        if(table[index] != null) return false;
        table[index] = value;
        if(size++ >= threshold) resize(tableCap * 2);
        return true;
    }

    private T swapRandom(int hash1, int hash2, int hash3, int hash4, T value){
        switch(RAND.nexti() & 3){
            case 0:
                return swap(hash1, value);
            case 1:
                return swap(hash2, value);
            case 2:
                return swap(hash3, value);
            default:
                return swap(hash4, value);
        }
    }

    private T swap(int index, T value){
        T old = table[index];
        table[index] = value;
        return old;
    }

    private void stash(T value){
        if(stashSize >= stashCap) resize(tableCap * 2);
        table[tableCap + stashSize++] = value;
        size++;
    }

    private void resize(int capacity){
        T[] old = table;
        setCap(capacity);
        table = create(tableCap + stashCap);
        size = stashSize = 0;
        for(int i = 0;i < old.length;i++) if(old[i] != null) place(old[i]);
    }

    @Override
    public Listerator<T> iterator(){
        return new SetIterator();
    }

    @Override
    public String toString(){
        return itrToString(this);
    }

    @Override
    public int size(){
        return size;
    }

    private class SetIterator implements Listerator<T>{
        public int nextIndex = 0;

        public SetIterator(){
        }

        public void findNextIndex(){
            for(;nextIndex < table.length;nextIndex++) if(table[nextIndex] != null) return;
        }

        @Override
        public boolean hasNext(){
            findNextIndex();
            return nextIndex < table.length;
        }

        @Override
        public T next(){
            findNextIndex();
            return table[nextIndex++];
        }

        @Override
        public int size(){
            return size;
        }
    }
}
