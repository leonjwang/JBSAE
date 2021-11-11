package jbsae.struct.prim;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class FloatSet{
    public boolean zero = false;
    public float[] table;
    public int size = 0;

    public FloatSet(){
        table = new float[16];
    }

    public FloatSet(float... values){
        this();
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public float[] list(){
        int i = 0;
        float[] values = new float[size];
        for(int j = 0;j < table.length;j++) if(table[j] != 0) values[i++] = table[j];
        if(zero) values[size - 1] = 0;
        return values;
    }

    public void add(float value){
        if(eqlf(value, 0)){
            zero = true;
            size++;
            return;
        }
        int h = intBits(value);
        int[] checks = hashes(h, table.length);
        for(int i = 0;i < checks.length;i++) if(eqlf(table[checks[i]], value)) return;
        for(int i = 0;i < checks.length;i++){
            if(table[checks[i]] == 0){
                table[checks[i]] = value;
                size++;
                return;
            }
        }
        resize(table.length << 1);
        add(value);
    }

    public void addAll(float... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public void remove(float value){
        if(eqlf(value, 0)){
            zero = false;
            size--;
            return;
        }
        int h = intBits(value);
        int[] checks = hashes(h, table.length);
        for(int i = 0;i < checks.length;i++){
            if(eqlf(table[checks[i]], value)){
                table[checks[i]] = 0;
                size--;
                return;
            }
        }
    }

    public void removeAll(float... values){
        for(int i = 0;i < values.length;i++) remove(values[i]);
    }

    public boolean contains(float value){
        if(eqlf(value, 0)) return zero;
        int h = intBits(value);
        int[] checks = hashes(h, table.length);
        for(int i = 0;i < checks.length;i++) if(eqlf(table[checks[i]], value)) return true;
        return false;
    }

    public void resize(int newSize){
        float[] table = this.table;
        float[] values = new float[size];
        int i = (size = 0);
        for(int j = 0;j < table.length;j++) if(table[j] != 0) values[i++] = table[j];
        this.table = new float[newSize];
        for(int j = 0;j < values.length;j++) add(values[j]);
    }
}