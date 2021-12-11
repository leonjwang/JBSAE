package jbsae.struct.tree;

//TODO: test

import jbsae.math.*;
import jbsae.struct.*;

import static jbsae.util.Mathf.*;

public class QuadTree<T extends Pos2> extends Tree<T>{
    public int depthLimit = 8;
    public Range2 range;
    public QuadTree branch1, branch2, branch3, branch4;

    public QuadTree(float w, float h){
        this(0, 0, w, h);
    }

    public QuadTree(float x, float y, float w, float h){
        this(new Range2(x, y, w, h));
    }

    public QuadTree(Range2 range){
        depthLimit = clamp((int)log(max(range.w, range.h), 2), 4, 16);
        branches = new Seq<>();
        values = new Seq<>();
        this.range = range;
    }

    public QuadTree limit(int depthLimit){
        this.depthLimit = depthLimit;
        return this;
    }

    public QuadTree find(Pos2 value){
        if(branches.size <= 0) return this;
        if(branch1.range.contains(value)) return branch1.find(value);
        else if(branch2.range.contains(value)) return branch2.find(value);
        else if(branch3.range.contains(value)) return branch3.find(value);
        else return branch4.find(value);
    }

    public Seq<T> findAll(Range2 range){
        if(branches.size <= 0) return values;
        Seq<T> arr = new Seq<>(values.list());
        if(branch1.range.overlaps(range)){
            arr.addAll(branch1.findAll(range));
        }
        if(branch2.range.overlaps(range)) arr.addAll(branch2.findAll(range));
        if(branch3.range.overlaps(range)) arr.addAll(branch3.findAll(range));
        if(branch4.range.overlaps(range)) arr.addAll(branch4.findAll(range));
        arr = arr == null ? branch1.findAll(range) : branch1.findAll(range);

        return arr;
    }

    @Override
    public void add(T value){
        if((values.size > 0 || branches.size > 0) && depthLimit > 0){
            if(branches.size <= 0){
                branch1 = new QuadTree(range.cpy().splt(2, 0)).limit(depthLimit - 1);
                branch2 = new QuadTree(range.cpy().splt(2, 1)).limit(depthLimit - 1);
                branch3 = new QuadTree(range.cpy().splt(2, 2)).limit(depthLimit - 1);
                branch4 = new QuadTree(range.cpy().splt(2, 3)).limit(depthLimit - 1);
                addBranches(branch1, branch2, branch3, branch4);
                addAll(values.get(0), value);
                values.remove(0);
            }else find(value).add(value);
        }else values.add(value);
    }

    @Override
    public void remove(T value){
        if(branches.size > 0) find(value).remove(value);
        else values.remove(value);
    }

    @Override
    public boolean contains(T value){
        if(branches.size > 0) return find(value).contains(value);
        else return values.contains(value);
    }
}

