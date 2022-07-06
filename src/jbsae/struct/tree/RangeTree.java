package jbsae.struct.tree;

import jbsae.math.*;
import jbsae.struct.*;

import static jbsae.util.Mathf.*;

public class RangeTree<T extends Pos4> extends Tree<T>{
    public int depthLimit, valueLimit = 4;
    public Range2 range;


    public RangeTree(float w, float h){
        this(0, 0, w, h);
    }

    public RangeTree(float x, float y, float w, float h){
        this(new Range2(x, y, w, h));
    }

    public RangeTree(Range2 range){
        depthLimit = clamp((int)log(max(range.w, range.h), 2), 4, 16);
        branches = new Seq<>();
        values = new Seq<>();
        this.range = range;
    }


    public RangeTree<T> valueLimit(int valueLimit){
        this.valueLimit = valueLimit;
        return this;
    }

    public RangeTree<T> depthLimit(int depthLimit){
        this.depthLimit = depthLimit;
        return this;
    }


    public Seq<T> query(Seq<T> arr, Set<T> used, Range2 range){
        if(branches.size > 0) for(Tree<T> branch : branches){
            RangeTree<T> b = (RangeTree<T>)branch;
            if(b.range.overlaps(range)) b.query(arr, used, range);
        }
        else for(T t : values){
            if(!used.contains(t)){
                arr.add(t);
                used.add(t);
            }
        }
        return arr;
    }

    public Seq<T> query(Seq<T> arr, Range2 range){
        return query(arr, new Set<T>(), range);
    }


    @Override
    public RangeTree<T> add(T value){
        if(!range.overlaps(value)) return this;
        if((values.size >= valueLimit || branches.size > 0) && depthLimit > 0){
            if(branches.size <= 0){
                for(int i = 0;i < 4;i++) addBranch(new RangeTree<T>(range.cpy().splt(2, i)).depthLimit(depthLimit - 1).valueLimit(valueLimit * 2));
                addAll(values).add(value);
                values.clear();
            }else for(int i = 0;i < 4;i++) if(((RangeTree<T>)branches.get(i)).range.overlaps(value)) branches.get(i).add(value);
        }else values.add(value);
        return this;
    }

    @Override
    public RangeTree<T> remove(T value){
        if(!range.overlaps(value)) return this;
        if(branches.size <= 0) values.remove(value);
        else for(int i = 0;i < 4;i++) if(((RangeTree<T>)branches.get(i)).range.overlaps(value)) branches.get(i).remove(value);
        return this;
    }

    @Override
    public boolean contains(T value){
        if(branches.size <= 0) return values.contains(value);
        else for(int i = 0;i < 4;i++) if(((RangeTree<T>)branches.get(i)).range.overlaps(value)) return branches.get(i).contains(value);
        return false;
    }
}
