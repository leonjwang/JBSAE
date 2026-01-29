package jbsae.struct.tree;

import jbsae.math.*;
import jbsae.struct.*;

import static jbsae.util.Mathf.*;

public class QuadTree<T extends Pos2> extends Tree<T>{
    private static final int MIN_DEPTH = 4;
    private static final int MAX_DEPTH = 16;

    private int depthLimit;
    private int valueLimit;
    private Range2 bounds;


    public QuadTree(float w, float h){
        this(0, 0, w, h);
    }

    public QuadTree(float x, float y, float w, float h){
        this(new Range2(x, y, w, h));
    }

    public QuadTree(Range2 bounds){
        super();
        this.depthLimit = clamp((int)log(max(bounds.w, bounds.h), 2), MIN_DEPTH, MAX_DEPTH);
        this.valueLimit = 4;
        this.bounds = bounds;
    }


    public QuadTree<T> valueLimit(int valueLimit){
        this.valueLimit = max(1, valueLimit);
        return this;
    }

    public QuadTree<T> depthLimit(int depthLimit){
        this.depthLimit = clamp(depthLimit, 1, MAX_DEPTH);
        return this;
    }


    private QuadTree<T> find(Pos2 value){
        if(branches.size == 0) return this;
        int quadrant = quadrant(value.x() - bounds.x - bounds.w / 2, value.y() - bounds.y - bounds.h / 2);
        return ((QuadTree<T>)branches.get(quadrant)).find(value);
    }

    public Seq<T> query(Range2 range){
        return query(new Seq<>(), range);
    }

    public Seq<T> query(Seq<T> result, Range2 range){
        if(!bounds.overlaps(range)) return result;
        if(branches.size == 0) result.addAll(values);
        else for(Tree t : branches) ((QuadTree<T>)t).query(result, range);

        return result;
    }


    @Override
    public QuadTree<T> add(T value){
        if(value == null || !bounds.contains(value)) return this;

        if(branches.size > 0){
            find(value).add(value);
            return this;
        }

        values.add(value);
        if(values.size > valueLimit && depthLimit > 0) subdivide();
        return this;
    }

    private void subdivide(){
        if(branches.size > 0) return;

        float w = bounds.w / 2, h = bounds.h / 2;
        for(int i = 0;i < 4;i++){
            addBranch(new QuadTree<T>(new Range2(bounds.x + QX[i] * w, bounds.y + QY[i] * h, w, h))
            .depthLimit(depthLimit - 1)
            .valueLimit(valueLimit));
        }

        for(T value : values) find(value).values.add(value);
        values = new Seq<>();
    }

    @Override
    public QuadTree<T> remove(T value){
        if(value == null || !bounds.contains(value)) return this;

        if(branches.size > 0) find(value).remove(value);
        else values.remove(value);

        return this;
    }

    @Override
    public boolean contains(T value){
        if(value == null || !bounds.contains(value)) return false;

        if(branches.size > 0) return find(value).contains(value);
        else return values.contains(value);
    }
}