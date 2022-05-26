package jbsae.struct.prim;

import jbsae.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class FloatfMap{
    public float zero;
    public float[] keys;
    public float[] values;
    public int size = 0;


    public FloatfMap(){
        keys = new float[16];
        values = new float[16];
        zero = nan;
    }

    public FloatfMap(float... entries){
        this();
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
        if(zero(key)){
            if(nan(zero)){
                zero = value;
                size++;
            }
            return this;
        }
        float[] keys = this.keys;
        float[] values = this.values;
        int h = intBits(key);
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eqlf(keys[checks[i]], key)) return this;
        for(int i = 0;i < checks.length;i++){
            if(zero(keys[checks[i]])){
                keys[checks[i]] = key;
                values[checks[i]] = value;
                size++;
                return this;
            }
        }
        return resize(keys.length << 1).add(key, value);
    }

    public FloatfMap addAll(float... entries){
        for(int i = 0;i < entries.length;i += 2) add(entries[i], entries[i + 1]);
        return this;
    }

    public FloatfMap remove(float key){
        if(zero(key)){
            if(!nan(zero)){
                zero = nan;
                size--;
            }
            return this;
        }
        float[] keys = this.keys;
        float[] values = this.values;
        int h = intBits(key);
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(eqlf(keys[checks[i]], key)){
                keys[checks[i]] = 0;
                values[checks[i]] = 0;
                size--;
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
        int h = intBits(key);
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eqlf(keys[checks[i]], key)) return values[checks[i]];
        return nan;
    }


    public boolean contains(float key){
        if(zero(key)) return !nan(zero);
        int h = intBits(key);
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eqlf(keys[checks[i]], key)) return true;
        return false;
    }

    public FloatfMap clear(){
        keys = new float[16];
        values = new float[16];
        size = 0;
        zero = nan;
        return this;
    }

    public FloatfMap resize(int newSize){
        float[] keys = keys();
        float[] values = values();
        size = 0;
        zero = nan;
        this.keys = new float[newSize];
        this.values = new float[newSize];
        for(int j = 0;j < keys.length;j++) add(keys[j], values[j]);
        return this;
    }
}
