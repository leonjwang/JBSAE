package jbsae.struct.tree;

import jbsae.math.*;
import jbsae.struct.*;

public class QuadTree<T extends Pos2>{
    private Seq<T> values;
    private int size;

    public QuadTree<T> b1, b2, b3, b4;

    private float cx, cy;

    public Range2 bounds;

    public int depthCap = 8, valueCap = 16;


    public QuadTree(float w, float h){
        this(0, 0, w, h);
    }

    public QuadTree(float x, float y, float w, float h){
        this(new Range2(x, y, w, h));
    }

    public QuadTree(Range2 bounds){
        values = new Seq<>(valueCap);
        setBounds(bounds);
    }

    public QuadTree<T> valueLimit(int valueLimit){
        this.valueCap = valueLimit;
        return this;
    }

    public QuadTree<T> depthLimit(int depthLimit){
        this.depthCap = depthLimit;
        return this;
    }

    private void setBounds(Range2 bounds){
        this.bounds = bounds;
        this.cx = bounds.x + bounds.w / 2;
        this.cy = bounds.y + bounds.h / 2;
    }


    private QuadTree<T> find(Pos2 value){
        if(b1 == null) return this;
        if(value.x() >= cx){
            if(value.y() >= cy) return b1;
            else return b4;
        }
        if(value.y() >= cy) return b2;
        else return b3;
    }

    public Seq<T> query(Range2 range){
        return query(new Seq<>(), range);
    }

    public Seq<T> query(Seq<T> result, Range2 range){
        result.ensure(estimate(range));
        append(result, range);
        return result;
    }

    private void append(Seq<T> result, Range2 range){
        if(b1 == null) result.addAll(values);
        else{
            if(b1.bounds.overlaps(range)) b1.append(result, range);
            if(b2.bounds.overlaps(range)) b2.append(result, range);
            if(b3.bounds.overlaps(range)) b3.append(result, range);
            if(b4.bounds.overlaps(range)) b4.append(result, range);
        }
    }

    private int estimate(Range2 range){
        if(b1 == null) return size;

        int total = 0;
        if(b1.bounds.overlaps(range)) total += b1.estimate(range);
        if(b2.bounds.overlaps(range)) total += b2.estimate(range);
        if(b3.bounds.overlaps(range)) total += b3.estimate(range);
        if(b4.bounds.overlaps(range)) total += b4.estimate(range);
        return total;
    }


    public QuadTree<T> add(T value){
        if(value == null || !bounds.contains(value)) return this;

        size++;
        if(b1 != null) find(value).add(value);
        else{
            values.add(value);
            if(size >= valueCap && depthCap > 0) subdivide();
        }
        return this;
    }

    private void subdivide(){
        if(b1 != null) return;

        float w2 = bounds.w / 2, h2 = bounds.h / 2;
        b1 = new QuadTree<T>(cx, cy, w2, h2);
        b2 = new QuadTree<T>(bounds.x, cy, w2, h2);
        b3 = new QuadTree<T>(bounds.x, bounds.y, w2, h2);
        b4 = new QuadTree<T>(cx, bounds.y, w2, h2);

        for(T value : values) find(value).add(value);
        values = null;
    }
}