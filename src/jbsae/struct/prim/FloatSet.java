package jbsae.struct.prim;

import jbsae.struct.*;
import jbsae.struct.prim.iterator.*;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;

public class FloatSet{
    public static boolean eqlf(float a, float b){
        return a == b;
    }

    private float[] table;
    public boolean hasZero;

    private int tableCap, stashCap;
    private int threshold, shift, mask, steps;

    private int stashSize;

    public float loadFactor = 0.8f;
    public int size;

    public FloatSet(){
        this(50);
    }

    public FloatSet(int capacity){
        setCap(nextPow2((int)(capacity / loadFactor + 1)));
        table = new float[tableCap + stashCap];
    }

    public FloatSet loadFactor(float loadFactor){
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

    public FloatSet add(float value){
        if(value == 0){
            if(!hasZero){
                hasZero = true;
                size++;
            }
            return this;
        }

        int base = Float.floatToIntBits(value);
        int hash1 = base & mask;
        int hash2 = hash(base, shift, PRIME1) & mask;
        int hash3 = hash(base, shift, PRIME2) & mask;

        if(eqlf(value, table[hash1])) return this;
        if(eqlf(value, table[hash2])) return this;
        if(eqlf(value, table[hash3])) return this;
        for(int i = 0;i < stashSize;i++) if(eqlf(value, table[tableCap + i])) return this;

        if(tryPlace(hash1, value)) return this;
        if(tryPlace(hash2, value)) return this;
        if(tryPlace(hash3, value)) return this;

        place(swapRandom(hash1, hash2, hash3, value));

        return this;
    }

    public FloatSet addAll(FloatIterator itr){
        if(itr instanceof Sized list) ensure(list.size());
        while(itr.hasNext()) add(itr.next());
        return this;
    }

    public boolean contains(float value){
        if(value == 0) return hasZero;

        int base = Float.floatToIntBits(value);
        if(eqlf(value, table[base & mask])) return true;
        if(eqlf(value, table[hash(base, shift, PRIME1) & mask])) return true;
        if(eqlf(value, table[hash(base, shift, PRIME2) & mask])) return true;
        for(int i = 0;i < stashSize;i++) if(eqlf(value, table[tableCap + i])) return true;
        return false;
    }

    public FloatSet remove(float value){
        if(value == 0){
            if(hasZero){
                hasZero = false;
                size--;
            }
            return this;
        }

        int base = Float.floatToIntBits(value);
        if(tryErase(base & mask, value)) return this;
        if(tryErase(hash(base, shift, PRIME1) & mask, value)) return this;
        if(tryErase(hash(base, shift, PRIME2) & mask, value)) return this;
        for(int i = 0;i < stashSize;i++)
            if(eqlf(value, table[tableCap + i])){
                table[tableCap + i] = table[tableCap + stashSize - 1];
                table[tableCap + stashSize - 1] = 0;
                stashSize--;
                size--;
                return this;
            }
        return this;
    }

    private boolean tryErase(int index, float value){
        if(!eqlf(value, table[index])) return false;
        table[index] = 0;
        size--;
        return true;
    }

    public FloatSet clear(){
        Arrays.fill(table, 0);
        hasZero = false;
        size = stashSize = 0;
        return this;
    }

    public FloatSet ensure(int space){
        int needed = (int)((size + space) / loadFactor + 1);
        if(needed > tableCap) resize(nextPow2(needed));
        return this;
    }

    private void place(float value){
        for(int i = 0;i < steps;i++){
            int base = Float.floatToIntBits(value);

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

    private boolean tryPlace(int index, float value){
        if(table[index] != 0) return false;
        table[index] = value;
        if(size++ >= threshold) resize(tableCap * 2);
        return true;
    }

    private float swapRandom(int hash1, int hash2, int hash3, float value){
        switch(abs(RAND.nexti()) % 3){
            case 0:
                return swap(hash1, value);
            case 1:
                return swap(hash2, value);
            default:
                return swap(hash3, value);
        }
    }

    private float swap(int index, float value){
        float old = table[index];
        table[index] = value;
        return old;
    }

    private void stash(float value){
        if(stashSize >= stashCap){
            resize(tableCap * 2);
            place(value);
            return;
        }
        table[tableCap + stashSize++] = value;
        size++;
    }

    private void resize(int capacity){
        float[] old = table;
        setCap(capacity);
        table = new float[tableCap + stashCap];
        size = hasZero ? 1 : 0;
        stashSize = 0;
        for(int i = 0;i < old.length;i++) if(old[i] != 0) place(old[i]);
    }

    public FloatIterator iterator(){
        return new SetIterator();
    }

    @Override
    public String toString(){
        return itrToString(iterator());
    }

    private class SetIterator extends FloatIterator implements Sized{
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
        public float nextf(){
            if(!zeroEmitted){
                zeroEmitted = true;
                return 0.0f;
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