package jbsae.struct.tree;

import jbsae.func.prim.*;
import jbsae.struct.*;

//TODO: Is this actually a useless class?
public class BinTree<T> extends Tree<T>{
    public Floatf<T> comparator;
    public int valueLimit = 4;
    public BinTree branch1, branch2;

    public BinTree(Floatf<T> comparator){
        this.comparator = comparator;
    }

    public BinTree<T> valueLimit(int valueLimit){
        this.valueLimit = valueLimit;
        return this;
    }

    @Override
    public BinTree<T> add(T value){
        if(branches.size > 0) return this;
        values.add(value);
        return this;
    }

    @Override
    public BinTree<T> addAll(T... values){
        for(int i = 0;i < values.length;i++) add(values[i]);
        return this;
    }

    @Override
    public BinTree<T> remove(T value){
        if(branches.size > 0) return this;
        values.remove(value);
        return this;
    }

    @Override
    public BinTree<T> removeAll(T... values){
        for(int i = 0;i < values.length;i++) remove(values[i]);
        return this;
    }

    /** This method must be used to utilize the BinTree. */
    public BinTree<T> format(){
        values.sort(comparator);
        if(values.size > valueLimit){
            T mid = values.get(values.size / 2);
            values.clear();
            branch1 = new BinTree<T>(comparator);
            branch2 = new BinTree<T>(comparator);
            //TODO: finish
        }
        return this;
    }

    public T find(Boolf<T> conditions){

        return null;
    }
}
