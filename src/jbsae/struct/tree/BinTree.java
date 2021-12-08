package jbsae.struct.tree;

import jbsae.func.prim.*;
import jbsae.struct.*;

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

    public void add(T value){
        if(values.size < 1 || limit <= 0) values.add(value);
        else{
            float mid = (min + max) / 2f;
            addBranches(new BinTree<T>(min, mid, comparator), new BinTree<T>(mid, max, comparator));
            if(comparator.get(value) < mid)
        }
    }
}
