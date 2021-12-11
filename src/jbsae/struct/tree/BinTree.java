package jbsae.struct.tree;

import jbsae.func.prim.*;
import jbsae.struct.*;

public class BinTree<T> extends Tree<T>{
    public Floatf<T> comparator;
    public float min, max;
    public int limit;
    public BinTree branch1, branch2;

    public BinTree(float min, float max, Floatf<T> comparator){
        this.comparator = comparator;
        this.min = min;
        this.max = max;
        limit = 16;
    }

    public BinTree<T> limit(int limit){
        this.limit = limit;
        return this;
    }

    @Override
    public BinTree<T> add(T value){
        float mid = (min + max) / 2f;
        if(branch1 != null){
            if(comparator.get(value) < mid) branch1.add(value);
            else branch2.add(value);
        }else if(values.size >= 1 && limit > 0){
            branch1 = new BinTree<T>(min, mid, comparator).limit(limit - 1);
            branch2 = new BinTree<T>(mid, max, comparator).limit(limit - 1);
            addBranches(branch1, branch2);
            addAll(values.get(0), value);
        }else values.add(value);
        return this;
    }

    @Override
    public BinTree<T> addAll(T... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    @Override
    public BinTree<T> remove(T value){
        float mid = (min + max) / 2f;
        if(branches.size > 0){
            if(comparator.get(value) < mid) branch1.remove(value);
            else branch2.remove(value);
        }else values.remove(value);
        return this;
    }

    @Override
    public BinTree<T> removeAll(T... values){
        for(int i = 0;i < values.length;i++) remove(values[i]);
        return this;
    }

    public T find(Boolf<T> conditions){
        if(values.size <= 0) return null;
        float mid = (min + max) / 2f;
        //TODO: this actually doesn't work
//        if(conditions.get(values.get(0))) return ((BinTree<T>)(comparator.get(values.get(0)) < mid ? branches.get(0) : branches.get(1))).find(conditions);
//        else return ((BinTree<T>)(comparator.get(values.get(0)) < mid ? branches.get(1) : branches.get(0))).find(conditions);
        return null;
    }

    public void clear(){
        values = new Seq<>();
        branches = new Seq<>();
    }
}
