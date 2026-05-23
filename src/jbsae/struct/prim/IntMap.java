package jbsae.struct.prim;

import jbsae.struct.*;
import jbsae.struct.prim.iterator.*;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;

public class IntMap<V>{
    private boolean eqli(int a, int b){
        return a == b;
    }

    private int[] keys;
    private V[] values;

    private V zeroValue;
    private boolean hasZeroValue;

    private int tableCap, stashCap;
    private int loadThreshold, shift, mask, steps;

    private int stashSize;

    public float loadFactor = 0.8f;
    public int size;

    public IntMap(){
        this(50);
    }

    public IntMap(int capacity){
        setCap(nextPow2((int)(capacity / loadFactor + 1)));
        keys = new int[tableCap + stashCap];
        values = (V[])new Object[tableCap + stashCap];
    }

    public IntMap<V> loadFactor(int loadFactor){
        this.loadFactor = loadFactor;
        this.loadThreshold = (int)(tableCap * loadFactor);
        return this;
    }

    private void setCap(int cap){
        this.tableCap = cap;
        this.stashCap = floorLog2(cap) * 2;
        this.loadThreshold = (int)(cap * loadFactor);
        this.shift = floorLog2(cap);
        this.mask = cap - 1;
        this.steps = (int)(rt2(cap) / 16 + 4);
    }

    public IntMap<V> add(int key, V value){
        if(eqli(key, 0)){
            if(!hasZeroValue){
                hasZeroValue = true;
                size++;
            }
            zeroValue = value;
            return this;
        }

        int base = key;
        int hash1 = base & mask;
        int hash2 = (hash(base, shift, PRIME1) & mask);
        int hash3 = (hash(base, shift, PRIME2) & mask);

        if(tryReplace(hash1, key, value)) return this;
        if(tryReplace(hash2, key, value)) return this;
        if(tryReplace(hash3, key, value)) return this;
        for(int i = 0;i < stashSize;i++) if(tryReplace(tableCap + i, key, value)) return this;

        if(tryPlace(hash1, key, value)) return this;
        if(tryPlace(hash2, key, value)) return this;
        if(tryPlace(hash3, key, value)) return this;

        swapRandom(hash1, hash2, hash3, key, value);
        key = displacedKey;
        value = displacedValue;
        place(key, value);
        displacedKey = 0;
        displacedValue = null;

        return this;
    }

    private boolean tryReplace(int index, int key, V value){
        if(!eqli(key, keys[index])) return false;
        values[index] = value;
        return true;
    }

    public V get(int key){
        if(eqli(key, 0)){
            return hasZeroValue ? zeroValue : null;
        }

        int base = key;
        int hash1 = base & mask;
        int hash2 = (hash(base, shift, PRIME1) & mask);
        int hash3 = (hash(base, shift, PRIME2) & mask);

        if(eqli(key, keys[hash1])) return values[hash1];
        if(eqli(key, keys[hash2])) return values[hash2];
        if(eqli(key, keys[hash3])) return values[hash3];

        for(int i = 0;i < stashSize;i++) if(eqli(key, keys[tableCap + i])) return values[tableCap + i];

        return null;
    }

    public IntMap<V> remove(int key){
        if(eqli(key, 0)){
            if(hasZeroValue){
                hasZeroValue = false;
                zeroValue = null;
                size--;
            }
            return this;
        }

        int base = key;
        if(tryErase(base & mask, key)) return this;
        if(tryErase((hash(base, shift, PRIME1) & mask), key)) return this;
        if(tryErase((hash(base, shift, PRIME2) & mask), key)) return this;
        for(int i = 0;i < stashSize;i++)
            if(eqli(key, keys[tableCap + i])){
                keys[tableCap + i] = keys[tableCap + stashSize - 1];
                values[tableCap + i] = values[tableCap + stashSize - 1];
                keys[tableCap + stashSize - 1] = 0;
                values[tableCap + stashSize - 1] = null;
                stashSize--;
                size--;
                return this;
            }
        return this;
    }

    private boolean tryErase(int index, int key){
        if(!eqli(key, keys[index])) return false;
        keys[index] = 0;
        values[index] = null;
        size--;
        return true;
    }

    public IntMap<V> clear(){
        Arrays.fill(keys, 0);
        Arrays.fill(values, null);
        size = stashSize = 0;
        hasZeroValue = false;
        zeroValue = null;
        return this;
    }

    public IntMap<V> ensure(int space){
        int needed = (int)((size + space) / loadFactor + 1);
        if(needed > tableCap) resize(nextPow2(needed));
        return this;
    }

    private int displacedKey = 0;
    private V displacedValue = null;

    private void place(int key, V value){
        for(int i = 0;i < steps;i++){
            int base = key;
            int hash1 = base & mask;
            if(tryPlace(hash1, key, value)) return;

            int hash2 = (hash(base, shift, PRIME1) & mask);
            if(tryPlace(hash2, key, value)) return;

            int hash3 = (hash(base, shift, PRIME2) & mask);
            if(tryPlace(hash3, key, value)) return;

            swapRandom(hash1, hash2, hash3, key, value);
            key = displacedKey;
            value = displacedValue;
        }

        stash(key, value);
    }

    private boolean tryPlace(int index, int key, V value){
        if(keys[index] != 0) return false;
        keys[index] = key;
        values[index] = value;
        if(size++ >= loadThreshold) resize(tableCap * 2);
        return true;
    }

    private void swapRandom(int hash1, int hash2, int hash3, int key, V value){
        switch(abs(RAND.nexti()) % 3){
            case 0:
                swap(hash1, key, value);
                return;
            case 1:
                swap(hash2, key, value);
                return;
            default:
                swap(hash3, key, value);
        }
    }

    private void swap(int index, int key, V value){
        displacedKey = keys[index];
        displacedValue = values[index];
        keys[index] = key;
        values[index] = value;
    }

    private void stash(int key, V value){
        if(stashSize >= stashCap){
            resize(tableCap * 2);
            place(key, value);
            return;
        }
        values[tableCap + stashSize] = value;
        keys[tableCap + stashSize++] = key;
        size++;
    }

    private void resize(int capacity){
        int[] oldKeys = keys;
        V[] oldVals = values;
        setCap(capacity);
        keys = new int[tableCap + stashCap];
        values = (V[])new Object[tableCap + stashCap];

        size = hasZeroValue ? 1 : 0;
        stashSize = 0;

        for(int i = 0;i < oldKeys.length;i++) if(oldKeys[i] != 0) place(oldKeys[i], oldVals[i]);
        displacedKey = 0;
        displacedValue = null;
    }

    public IntIterator iterator(){
        return new KeyIterator();
    }

    public Iterator<V> values(){
        return new ValIterator();
    }

    @Override
    public String toString(){
        return itrToString(iterator());
    }

    private class KeyIterator extends IntIterator implements Sized{
        public int nextIndex = 0;
        public boolean yieldedZero = !hasZeroValue;

        public KeyIterator(){
        }

        public void findNextIndex(){
            for(;nextIndex < keys.length;nextIndex++) if(keys[nextIndex] != 0) return;
        }

        @Override
        public boolean hasNext(){
            if(!yieldedZero) return true;
            findNextIndex();
            return nextIndex < keys.length;
        }

        @Override
        public int nexti(){
            if(!yieldedZero){
                yieldedZero = true;
                return 0;
            }
            findNextIndex();
            return keys[nextIndex++];
        }

        @Override
        public int size(){
            return size;
        }
    }

    private class ValIterator implements Iterator<V>, Sized{
        public int nextIndex = 0;
        public boolean yieldedZero = !hasZeroValue;

        public ValIterator(){
        }

        public void findNextIndex(){
            for(;nextIndex < keys.length;nextIndex++) if(keys[nextIndex] != 0) return;
        }

        @Override
        public boolean hasNext(){
            if(!yieldedZero) return true;
            findNextIndex();
            return nextIndex < keys.length;
        }

        @Override
        public V next(){
            if(!yieldedZero){
                yieldedZero = true;
                return zeroValue;
            }
            findNextIndex();
            return values[nextIndex++];
        }

        @Override
        public int size(){
            return size;
        }
    }
}