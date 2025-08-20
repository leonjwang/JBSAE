package jbsae.struct.prim;

import jbsae.*;
import jbsae.func.prim.*;
import jbsae.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

/** Important note: Floatmaps and Floatsets may not have the same behavior as a HashMap or HashSet depending on exact field. */
public class FloatSet{
    public boolean zero = false;
    public float[] table;
    public int size = 0;
    /** If true, uses exact equality for keys, otherwise uses eqlf. */
    public boolean exact = true;


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
        for(int j = 0;j < table.length;j++) if(!eqlf(table[j], 0)) values[i++] = table[j];
        if(zero) values[size - 1] = 0;
        return values;
    }

    public FloatSet add(float value){
        if(eqlf(value, 0)){
            if(!zero){
                zero = true;
                size++;
            }
            return this;
        }
        int steps = (trailZeros(table.length) << 1) + 1;
        for(int step = 0;step < steps;step++){
            int[] checks = hash3(intBits(value), table.length, Tmp.i3);
            for(int i = 0;i < checks.length;i++) if(eqlf(table[checks[i]], value)) return this;
            for(int i = 0;i < checks.length;i++){
                int index = checks[i];
                if(table[index] == 0){
                    table[index] = value;
                    size++;
                    return this;
                }
            }
            int index = checks[randInt(0, checks.length - 1)];
            float displaced = table[index];
            table[index] = value;
            value = displaced;
        }
        return resize(table.length << 1).add(value);
    }

    public FloatSet addAll(float... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    public FloatSet remove(float value){
        if(eqlf(value, 0)){
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
        if(eqlf(value, 0)) return zero;
        int[] checks = hash3(intBits(value), table.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eqlf(table[checks[i]], value)) return true;
        return false;
    }

    public FloatSet each(Floatc cons){
        if(zero) cons.get(0);
        for(int j = 0;j < table.length;j++) if(!eqlf(table[j], 0)) cons.get(table[j]);
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


    private boolean eqlf(float a, float b){
        return exact ? a == b : Mathf.eqlf(a, b);
    }
}