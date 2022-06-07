package jbsae.struct.prim;

import jbsae.*;
import jbsae.func.prim.*;

import static jbsae.util.Structf.*;

public class IntSet{
    public boolean zero = false;
    public int[] table;
    public int size = 0;

    public IntSet(){
        table = new int[16];
    }

    public IntSet(int... values){
        this();
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public int[] list(){
        int i = 0;
        int[] values = new int[size];
        for(int j = 0;j < table.length;j++) if(table[j] != 0) values[i++] = table[j];
        if(zero) values[size - 1] = 0;
        return values;
    }

    public IntSet add(int value){
        if(value == 0){
            if(!zero){
                zero = true;
                size++;
            }
            return this;
        }
        int[] checks = hash3(value, table.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(table[checks[i]] == value) return this;
        for(int i = 0;i < checks.length;i++){
            if(table[checks[i]] == 0){
                table[checks[i]] = value;
                size++;
                return this;
            }
        }
        return resize(table.length << 1).add(value);
    }

    public IntSet addAll(int... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    public IntSet remove(int value){
        if(value == 0){
            if(zero){
                zero = false;
                size--;
            }
            return this;
        }
        int[] checks = hash3(value, table.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(table[checks[i]] == value){
                table[checks[i]] = 0;
                size--;
                return this;
            }
        }
        return this;
    }

    public IntSet removeAll(int... values){
        for(int i = 0;i < values.length;i++) remove(values[i]);
        return this;
    }

    public boolean contains(int value){
        if(value == 0) return zero;
        int[] checks = hash3(value, table.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(table[checks[i]] == value) return true;
        return false;
    }

    public IntSet each(Intc cons){
        if(zero) cons.get(0);
        for(int j = 0;j < table.length;j++) if(table[j] != 0) cons.get(table[j]);
        return this;
    }

    public IntSet clear(){
        fill(table, 0);
        zero = false;
        size = 0;
        return this;
    }

    public IntSet resize(int newSize){
        int[] values = list();
        size = 0;
        zero = false;
        this.table = new int[newSize];
        for(int j = 0;j < values.length;j++) add(values[j]);
        return this;
    }
}
