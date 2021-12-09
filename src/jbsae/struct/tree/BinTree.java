package jbsae.struct.tree;

import jbsae.func.prim.*;
import jbsae.struct.*;

//TODO: finish
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
        if(values.size >= 1 && limit > 0){
            float mid = (min + max) / 2f;

            addBranches(new BinTree<T>(min, mid, comparator).limit(limit - 1), new BinTree<T>(mid, max, comparator).limit(limit - 1));

            if(comparator.get(value) < mid) branches.get(0).add(value);
            else branches.get(1).add(value);
        }else values.add(value);
    }
}
