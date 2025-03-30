package jbsae.struct.prim;

import jbsae.*;
import jbsae.func.prim.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class FloatSet{
    public boolean zero = false;
    public float[] table;
    public int size = 0;

    public FloatSet(){
        this(16);
    }

    public FloatSet(int size){
        table = new float[size];
    }

    public FloatSet(float... values){
        this(values.length);
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public float[] list(){
        int i = 0;
        float[] values = new float[size];
        for(int j = 0;j < table.length;j++) if(!zero(table[j])) values[i++] = table[j];
        if(zero) values[size - 1] = 0;
        return values;
    }

    public FloatSet add(float value){
        if(zero(value)){
            if(!zero){
                zero = true;
                size++;
            }
            return this;
        }
        int[] checks = hash3(intBits(value), table.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eqlf(table[checks[i]], value)) return this;
        for(int i = 0;i < checks.length;i++){
            if(zero(table[checks[i]])){
                table[checks[i]] = value;
                size++;
                return this;
            }
        }
        return resize(table.length << 1).add(value);
    }

    public FloatSet addAll(float... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    public FloatSet remove(float value){
        if(zero(value)){
            if(zero){
                zero = false;
                size--;
            }
            return this;
        }
        int[] checks = hash3(intBits(value), table.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(eqlf(table[checks[i]], value)){
                table[checks[i]] = 0;
                size--;
                return this;
            }
        }
        return this;
    }

    public FloatSet removeAll(float... values){
        for(int i = 0;i < values.length;i++) remove(values[i]);
        return this;
    }

    public boolean contains(float value){
        if(zero(value)) return zero;
        int[] checks = hash3(intBits(value), table.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eqlf(table[checks[i]], value)) return true;
        return false;
    }

    public FloatSet each(Floatc cons){
        if(zero) cons.get(0);
        for(int j = 0;j < table.length;j++) if(!zero(table[j])) cons.get(table[j]);
        return this;
    }

    public FloatSet clear(){
        fill(table, 0);
        zero = false;
        size = 0;
        return this;
    }

    public FloatSet resize(int newSize){
        float[] values = list();
        size = 0;
        zero = false;
        this.table = new float[newSize];
        for(int j = 0;j < values.length;j++) add(values[j]);
        return this;
    }
}