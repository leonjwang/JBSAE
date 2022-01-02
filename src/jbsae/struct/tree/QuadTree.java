package jbsae.struct.tree;

import jbsae.math.*;
import jbsae.struct.*;

import static jbsae.util.Mathf.*;

public class QuadTree<T extends Pos2> extends Tree<T>{
    public int depthLimit, valueLimit = 4;
    public Range2 range;


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


    public QuadTree<T> valueLimit(int valueLimit){
        this.valueLimit = valueLimit;
        return this;
    }

    public QuadTree<T> depthLimit(int depthLimit){
        this.depthLimit = depthLimit;
        return this;
    }


    public QuadTree<T> find(Pos2 value){
        if(branches.size <= 0) return this;
        for(Tree<T> branch : branches) if(((QuadTree<T>)branch).range.contains(value)) return ((QuadTree<T>)branch).find(value);
        return this;
    }

    public Seq<T> findAll(Range2 range){
        if(branches.size <= 0) return values;
        Seq<T> arr = new Seq<>();
        for(Tree<T> branch : branches){
            QuadTree<T> b = (QuadTree<T>)branch;
            if(b.range.overlaps(range)) arr = (arr.size == 0) ? arr.set(b.findAll(range)) : arr.addAll(b.findAll(range));
        }
        return arr;
    }


    @Override
    public QuadTree<T> add(T value){
        if((values.size >= valueLimit || branches.size > 0) && depthLimit > 0){
            if(branches.size <= 0){
                addBranches(
                new QuadTree<T>(range.cpy().splt(2, 0)).depthLimit(depthLimit - 1).valueLimit(valueLimit),
                new QuadTree<T>(range.cpy().splt(2, 1)).depthLimit(depthLimit - 1).valueLimit(valueLimit),
                new QuadTree<T>(range.cpy().splt(2, 2)).depthLimit(depthLimit - 1).valueLimit(valueLimit),
                new QuadTree<T>(range.cpy().splt(2, 3)).depthLimit(depthLimit - 1).valueLimit(valueLimit)
                );
                addAll(values).add(value);
                values.clear();
            }else find(value).add(value);
        }else values.add(value);
        return this;
    }

    @Override
    public QuadTree<T> remove(T value){
        if(branches.size > 0) find(value).remove(value);
        else values.remove(value);
        return this;
    }

    @Override
    public boolean contains(T value){
        if(branches.size > 0) return find(value).contains(value);
        else return values.contains(value);
    }
}

