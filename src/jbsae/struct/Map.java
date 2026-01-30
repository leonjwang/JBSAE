package jbsae.struct;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;

public class Map<K, V> implements Iterable<K>{
    private K[] keys;
    private V[] values;

    private int tableCap, stashCap;
    private int threshold, shift, mask, steps;

    private int stashSize;

    public float loadFactor = 0.8f;
    public int size;

    public Map(){
        this(128);
    }

    public Map(int capacity){
        setCap(nextPow2(capacity));
        keys = create(tableCap + stashCap);
        values = create(tableCap + stashCap);
    }

    public Map<K, V> loadFactor(float loadFactor){
        this.loadFactor = loadFactor;
        this.threshold = (int)(tableCap * loadFactor);
        return this;
    }


    private void setCap(int cap){
        this.tableCap = cap;
        this.stashCap = floorLog2(cap);
        this.threshold = (int)(cap * loadFactor);
        this.shift = floorLog2(cap);
        this.mask = cap - 1;
        this.steps = (int)(rt2(cap) / 8 + 4);
    }

    private int conv(int hash){
        return hash & mask;
    }

    public Map<K, V> add(K key, V value){
        if(key == null) throw new RuntimeException("Value is null");

        int base = key.hashCode();
        int hash1 = conv(base);
        int hash2 = conv(hash(base, shift, PRIME1));
        int hash3 = conv(hash(base, shift, PRIME2));
        int hash4 = conv(hash(base, shift, PRIME3));

        if(tryReplace(hash1, key, value)) return this;
        if(tryReplace(hash2, key, value)) return this;
        if(tryReplace(hash3, key, value)) return this;
        if(tryReplace(hash4, key, value)) return this;

        if(tryPlace(hash1, key, value)) return this;
        if(tryPlace(hash2, key, value)) return this;
        if(tryPlace(hash3, key, value)) return this;
        if(tryPlace(hash4, key, value)) return this;

        addi(swapRandom(hash1, hash2, hash3, hash4, key, value), value);

        return this;
    }

    private boolean tryReplace(int index, K key, V value){
        if(!key.equals(keys[index])) return false;
        values[index] = value;
        return true;
    }

    public V get(K key){
        int base = key.hashCode();
        int hash1 = conv(base);
        int hash2 = conv(hash(base, shift,  PRIME1));
        int hash3 = conv(hash(base, shift, PRIME2));
        int hash4 = conv(hash(base, shift, PRIME3));
        if(key.equals(keys[hash1])) return values[hash1];
        if(key.equals(keys[hash2])) return values[hash2];
        if(key.equals(keys[hash3])) return values[hash3];
        if(key.equals(keys[hash4])) return values[hash4];
        return null;
    }

    public Map<K, V> remove(K key){
        if(key == null) throw new RuntimeException("Value is null");
        int base = key.hashCode();
        if(tryErase(conv(base), key)) return this;
        if(tryErase(conv(hash(base, shift, PRIME1)), key)) return this;
        if(tryErase(conv(hash(base, shift, PRIME2)), key)) return this;
        if(tryErase(conv(hash(base, shift, PRIME3)), key)) return this;
        for(int i = 0;i < stashSize;i++) if(tryErase(tableCap + i, key)) return this;
        return this;
    }

    private boolean tryErase(int index, K key){
        if(!key.equals(keys[index])) return false;
        keys[index] = null;
        values[index] = null;
        size--;
        return true;
    }

    public Map<K, V> ensure(int space){
        int needed = (int)((size + space) / loadFactor + 1);
        if(needed > tableCap) resize(tableCap * 2);
        return this;
    }

    private void addi(K key, V value){
        for(int i = 0;i < steps;i++){
            int base = key.hashCode();
            int hash1 = conv(base);
            if(tryPlace(hash1, key, value)) return;

            int hash2 = conv(hash(base, shift, PRIME1));
            if(tryPlace(hash2, key, value)) return;

            int hash3 = conv(hash(base,shift,  PRIME2));
            if(tryPlace(hash3, key, value)) return;

            int hash4 = conv(hash(base, shift, PRIME3));
            if(tryPlace(hash4, key, value)) return;

            key = swapRandom(hash1, hash2, hash3, hash4, key, value);
        }

        stash(key, value);
    }

    private boolean tryPlace(int index, K key, V value){
        if(keys[index] != null) return false;
        keys[index] = key;
        values[index] = value;
        if(size++ >= threshold) resize(tableCap * 2);
        return true;
    }

    private K swapRandom(int hash1, int hash2, int hash3, int hash4, K key, V value){
        switch(RAND.nexti() & 3){
            case 0:
                return swap(hash1, key, value);
            case 1:
                return swap(hash2, key, value);
            case 2:
                return swap(hash3, key, value);
            default:
                return swap(hash4, key, value);
        }
    }

    private K swap(int index, K key, V value){
        K old = keys[index];
        keys[index] = key;
        values[index] = value;
        return old;
    }

    private void stash(K key, V value){
        while(stashSize >= stashCap) resize(tableCap * 2);
        values[tableCap + stashSize] = value;
        keys[tableCap + stashSize++] = key;
        size++;
    }

    private void resize(int capacity){
        K[] oldKeys = keys;
        V[] oldVals = values;
        setCap(capacity);
        keys = create(tableCap + stashCap);
        values = create(tableCap + stashCap);
        size = stashSize = 0;
        for(int i = 0;i < oldKeys.length;i++) if(oldKeys[i] != null) addi(oldKeys[i], oldVals[i]);
    }

    @Override
    public Iterator<K> iterator(){
        return new SetIterator();
    }

    @Override
    public String toString(){
        return itrToString(this);
    }

    private class SetIterator implements Iterator<K>{
        public int nextIndex = 0;

        public SetIterator(){
        }

        public void findNextIndex(){
            for(;nextIndex < keys.length;nextIndex++) if(keys[nextIndex] != null) return;
        }

        @Override
        public boolean hasNext(){
            findNextIndex();
            return nextIndex < keys.length;
        }

        @Override
        public K next(){
            return keys[nextIndex++];
        }
    }
}
