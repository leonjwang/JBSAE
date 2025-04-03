package jbsae.struct.prim;

import jbsae.*;
import jbsae.func.prim.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class FloatfMap{
    public float zero;
    public float[] keys;
    public float[] values;
    public int size = 0;


    public FloatfMap(){
        this(16);
    }

    public FloatfMap(int size){
        keys = new float[size];
        values = new float[size];
        zero = NAN;
    }

    public FloatfMap(float... entries){
        this(entries.length);
        for(int i = 0;i < entries.length;i += 2) add(entries[i], entries[i + 1]);
    }


    public float[] keys(){
        int i = 0;
        float[] values = new float[size];
        for(int j = 0;j < keys.length;j++) if(keys[j] != 0) values[i++] = keys[j];
        if(!nan(zero)) values[size - 1] = 0;
        return values;
    }

    public float[] values(){
        float[] keys = keys();
        float[] values = new float[size];
        for(int i = 0;i < keys.length;i++) values[i] = get(keys[i]);
        return values;
    }


    public FloatfMap add(float key, float value){
        if(nan(value)) return this;
        if(zero(key)) return setZero(value);
        int steps = (trailZeros(keys.length) << 1) + 1;
        for(int step = 0;step < steps;step++){
            int[] checks =  hash3(intBits(key), keys.length, Tmp.i3);
            for(int i = 0;step == 0 && i < checks.length;i++) if(eqlf(keys[checks[i]], key)) return set(checks[i], key, value);
            for(int i = 0;i < checks.length;i++) if(keys[checks[i]] == 0) return set(checks[i], key, value);
            int index = checks[randInt(0, checks.length - 1)];
            float displacedKey = keys[index];
            float displacedValue = values[index];
            keys[index] = key;
            values[index] = value;
            key = displacedKey;
            value = displacedValue;
        }
        resize(keys.length << 1);
        return add(key, value);
    }

    private FloatfMap set(int i, float key, float value){
        if(keys[i] == 0) size++;
        keys[i] = key;
        values[i] = value;
        return this;
    }

    private FloatfMap setZero(float value){
        if(nan(zero)) size++;
        zero = value;
        return this;
    }

    public FloatfMap addAll(float... entries){
        for(int i = 0;i < entries.length;i += 2) add(entries[i], entries[i + 1]);
        return this;
    }

    public FloatfMap remove(float key){
        if(zero(key)){
            if(!nan(zero)){
                zero = NAN;
                size--;
            }
            return this;
        }
        float[] keys = this.keys;
        float[] values = this.values;
        int[] checks = hash3(intBits(key), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(eqlf(keys[checks[i]], key)){
                keys[checks[i]] = 0;
                values[checks[i]] = 0;
                size--;
                return this;
            }
        }
        return this;
    }

    public FloatfMap removeAll(float... keys){
        for(int i = 0;i < keys.length;i++) remove(keys[i]);
        return this;
    }


    public float get(float key){
        if(zero(key)) return zero;
        int[] checks = hash3(intBits(key), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eqlf(keys[checks[i]], key)) return values[checks[i]];
        return NAN;
    }


    public boolean contains(float key){
        if(zero(key)) return !nan(zero);
        int[] checks = hash3(intBits(key), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eqlf(keys[checks[i]], key)) return true;
        return false;
    }

    public FloatfMap eachKey(Floatc cons){
        if(!nan(zero)) cons.get(0);
        for(int j = 0;j < keys.length;j++) if(keys[j] != 0) cons.get(keys[j]);
        return this;
    }

    public FloatfMap clear(){
        fill(keys, 0);
        fill(values, 0);
        size = 0;
        zero = NAN;
        return this;
    }

    public FloatfMap resize(int newSize){
        float[] keys = keys();
        float[] values = values();
        size = 0;
        zero = NAN;
        this.keys = new float[newSize];
        this.values = new float[newSize];
        for(int j = 0;j < keys.length;j++) add(keys[j], values[j]);
        return this;
    }
}
