package jbsae.struct.tree;

import jbsae.func.prim.*;
import jbsae.struct.*;

//TODO: test
public class BinTree<T> extends Tree<T>{
    public Floatf<T> comparator;
    public float min, max;
    public int limit;

    public BinTree(float min, float max, Floatf<T> comparator){
        this.comparator = comparator;
        this.min = min;
        this.max = max;
        limit = 16;
    }

    public BinTree limit(int limit){
        this.limit = limit;
        return this;
    }

    public void add(T value){
        float mid = (min + max) / 2f;
        if(branches.size > 0){
            if(comparator.get(value) < mid) branches.get(0).add(value);
            else branches.get(1).add(value);
        }else if(values.size >= 1 && limit > 0){
            addBranches(new BinTree<T>(min, mid, comparator).limit(limit - 1), new BinTree<T>(mid, max, comparator).limit(limit - 1));
            addAll(values.get(0), value);
        }else values.add(value);
    }

    public void addAll(T... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
    }

    public void remove(T value){
        float mid = (min + max) / 2f;
        if(branches.size > 0){
            if(comparator.get(value) < mid) branches.get(0).remove(value);
            else branches.get(1).remove(value);
        }else values.remove(value);
    }

    public void removeAll(T... values){
        for(int i = 0;i < values.length;i++) remove(values[i]);
    }

    public T find(Boolf<T> conditions){
        if(values.size <= 0) return null;
        float mid = (min + max) / 2f;
        if(conditions.get(values.get(0))) return ((BinTree<T>)(comparator.get(values.get(0)) < mid ? branches.get(0) : branches.get(1))).find(conditions);
        else return ((BinTree<T>)(comparator.get(values.get(0)) < mid ? branches.get(1) : branches.get(0))).find(conditions);
    }

    public void clear(){
        values = new Seq<>();
        branches = new Seq<>();
    }
}
