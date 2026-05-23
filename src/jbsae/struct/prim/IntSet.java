package jbsae.struct.prim;

import jbsae.struct.*;
import jbsae.struct.prim.iterator.*;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;

public class IntSet{
    public static boolean eqli(int a, int b) {
        return a == b;
    }

    private int[] table;
    public boolean hasZero;

    private int tableCap, stashCap;
    private int threshold, shift, mask, steps;

    private int stashSize;

    public float loadFactor = 0.8f;
    public int size;

    public IntSet(){
        this(50);
    }

    public IntSet(int capacity){
        setCap(nextPow2((int)(capacity / loadFactor + 1)));
        table = new int[tableCap + stashCap];
    }

    public IntSet loadFactor(int loadFactor){
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

    public IntSet add(int value){
        if(value == 0){
            if(!hasZero){
                hasZero = true;
                size++;
            }
            return this;
        }

        int base = value;
        int hash1 = base & mask;
        int hash2 = hash(base, shift, PRIME1) & mask;
        int hash3 = hash(base, shift, PRIME2) & mask;

        if(eqli(value, table[hash1])) return this;
        if(eqli(value, table[hash2])) return this;
        if(eqli(value, table[hash3])) return this;
        for(int i = 0;i < stashSize;i++) if(eqli(value, table[tableCap + i])) return this;

        if(tryPlace(hash1, value)) return this;
        if(tryPlace(hash2, value)) return this;
        if(tryPlace(hash3, value)) return this;

        place(swapRandom(hash1, hash2, hash3, value));

        return this;
    }

    public IntSet addAll(IntIterator itr){
        if(itr instanceof Sized list) ensure(list.size());
        while(itr.hasNext()) add(itr.next());
        return this;
    }

    public boolean contains(int value){
        if(value == 0) return hasZero;

        int base = value;
        if(eqli(value, table[base & mask])) return true;
        if(eqli(value, table[hash(base, shift, PRIME1) & mask])) return true;
        if(eqli(value, table[hash(base, shift, PRIME2) & mask])) return true;
        for(int i = 0;i < stashSize;i++) if(eqli(value, table[tableCap + i])) return true;
        return false;
    }

    public IntSet remove(int value){
        if(value == 0){
            if(hasZero){
                hasZero = false;
                size--;
            }
            return this;
        }

        int base = value;
        if(tryErase(base & mask, value)) return this;
        if(tryErase(hash(base, shift, PRIME1) & mask, value)) return this;
        if(tryErase(hash(base, shift, PRIME2) & mask, value)) return this;
        for(int i = 0;i < stashSize;i++) if(eqli(value, table[tableCap + i])){
            table[tableCap + i] = table[tableCap + stashSize - 1];
            table[tableCap + stashSize - 1] = 0;
            stashSize--;
            size--;
            return this;
        }
        return this;
    }

    private boolean tryErase(int index, int value){
        if(!eqli(value, table[index])) return false;
        table[index] = 0;
        size--;
        return true;
    }

    public IntSet clear(){
        Arrays.fill(table, 0);
        hasZero = false;
        size = stashSize = 0;
        return this;
    }

    public IntSet ensure(int space){
        int needed = (int)((size + space) / loadFactor + 1);
        if(needed > tableCap) resize(nextPow2(needed));
        return this;
    }

    private void place(int value){
        for(int i = 0;i < steps;i++){
            int base = value;

            int hash1 = base & mask;
            if(tryPlace(hash1, value)) return;

            int hash2 = hash(base, shift, PRIME1) & mask;
            if(tryPlace(hash2, value)) return;

            int hash3 = hash(base, shift, PRIME2) & mask;
            if(tryPlace(hash3, value)) return;

            value = swapRandom(hash1, hash2, hash3, value);
        }

        stash(value);
    }

    private boolean tryPlace(int index, int value){
        if(table[index] != 0) return false;
        table[index] = value;
        if(size++ >= threshold) resize(tableCap * 2);
        return true;
    }

    private int swapRandom(int hash1, int hash2, int hash3, int value){
        switch(abs(RAND.nexti()) % 3){
            case 0:
                return swap(hash1, value);
            case 1:
                return swap(hash2, value);
            default:
                return swap(hash3, value);
        }
    }

    private int swap(int index, int value){
        int old = table[index];
        table[index] = value;
        return old;
    }

    private void stash(int value){
        if(stashSize >= stashCap){
            resize(tableCap * 2);
            place(value);
            return;
        }
        table[tableCap + stashSize++] = value;
        size++;
    }

    private void resize(int capacity){
        int[] old = table;
        setCap(capacity);
        table = new int[tableCap + stashCap];
        size = hasZero ? 1 : 0;
        stashSize = 0;
        for(int i = 0;i < old.length;i++) if(old[i] != 0) place(old[i]);
    }

    public IntIterator iterator(){
        return new SetIterator();
    }

    @Override
    public String toString(){
        return itrToString(iterator());
    }

    private class SetIterator extends IntIterator implements Sized{
        public int nextIndex = 0;
        public boolean zeroEmitted = !hasZero;

        public SetIterator(){
        }

        public void findNextIndex(){
            for(;nextIndex < table.length;nextIndex++) if(table[nextIndex] != 0) return;
        }

        @Override
        public boolean hasNext(){
            if(!zeroEmitted) return true;
            findNextIndex();
            return nextIndex < table.length;
        }

        @Override
        public int nexti(){
            if(!zeroEmitted){
                zeroEmitted = true;
                return 0;
            }
            findNextIndex();
            return table[nextIndex++];
        }

        @Override
        public int size(){
            return size;
        }
    }
}