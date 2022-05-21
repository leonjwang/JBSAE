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

    public Seq<T> findAll(Seq<T> arr, Range2 range){
        if(branches.size <= 0) arr.addAll(values);
        else for(Tree<T> branch : branches){
            QuadTree<T> b = (QuadTree<T>)branch;
            if(b.range.overlaps(range)) b.findAll(arr, range);
        }
        return arr;
    }


    @Override
    public QuadTree<T> add(T value){
        if(!range.contains(value)) return this;
        if((values.size >= valueLimit || branches.size > 0) && depthLimit > 0){
            if(branches.size <= 0){
                for(int i = 0;i < 4;i++) addBranch(new QuadTree<T>(range.cpy().splt(2, i)).depthLimit(depthLimit - 1).valueLimit(valueLimit));
                addAll(values).add(value);
                values.clear();
            }else find(value).add(value);
        }else values.add(value);
        return this;
    }

    @Override
    public QuadTree<T> remove(T value){
        if(!range.contains(value)) return this;
        if(branches.size > 0) find(value).remove(value);
        else values.remove(value);
        return this;
    }

    @Override
    public boolean contains(T value){
        if(branches.size > 0) return find(value).contains(value);
        else return values.contains(value);
    }

    public static void main(String[] args){
//        Seq<Vec2> original = new Seq<>();
//        QuadTree tree = new QuadTree(10000, 10000).valueLimit(8);
//        for(int i = 0;i < 10000;i++){
//            Vec2 v = new Vec2(random(0, 10000), random(0, 10000));
//            tree.add(v);
//            original.add(v);
//        }
//
//        Range2 range = new Range2(random(0, 9000), random(0, 9000), random(0, 1000), random(0, 1000));
//        Seq<Pos2> inside = new Seq<>();
//        tree.findAll(inside, range);
//        Seq<Pos2> trueInside = new Seq<>();
//        for(Vec2 v : original){
//            if(range.contains(v)){
//                trueInside.add(v);
//                if(!inside.contains(v)) System.out.println("No");
//            }
//        }
//        if(inside.size > trueInside.size * 2 + 100) System.out.println("False");

        Seq<Vec2> original = new Seq<>();
        QuadTree tree = new QuadTree(10000, 10000).valueLimit(8);
        for(int i = 0;i < 1000000;i++){
            Vec2 v = new Vec2(random(0, 10000), random(0, 10000));
            tree.add(v);
            original.add(v);
        }
        Range2 range = new Range2(random(0, 9000), random(0, 9000), random(0, 1000), random(0, 1000));
        Seq<Pos2> inside = new Seq<>();

        long start = System.currentTimeMillis();
        for(Vec2 v : original) if(range.contains(v)) inside.add(v);
        System.out.println(System.currentTimeMillis() - start);

        inside.clear();
        start = System.currentTimeMillis();
        tree.findAll(inside, range);
        System.out.println(System.currentTimeMillis() - start);
    }
}

