package jbsae.struct.tree;

import jbsae.func.prim.*;
import jbsae.struct.*;

import static jbsae.util.Mathf.*;

// TODO: Optimize with 3-4 tree
public class BinTree<T> extends Tree<T>{
    public Floatf<T> comparator;
    public boolean formatted;

    public BinTree(Floatf<T> comparator){
        this.comparator = comparator;
    }

    @Override
    public BinTree<T> add(T value){
        formatted = false;
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
        formatted = false;
        if(branches.size > 0) return this;
        values.remove(value);
        return this;
    }

    @Override
    public BinTree<T> removeAll(T... values){
        for(int i = 0;i < values.length;i++) remove(values[i]);
        return this;
    }

    public T key(){
        return values.size == 0 ? null : values.get(values.size / 2);
    }

    public BinTree<T> left(){
        return branches.size == 0 ? null : (BinTree<T>)branches.get(0);
    }

    public BinTree<T> right(){
        return branches.size == 0 ? null : (BinTree<T>)branches.get(1);
    }

    public BinTree<T> format(){
        formatted = true;

        if(values.size <= 1) return this;

        if(parent == null){
            Seq<T> all = new Seq<>();
            for(T t : this) all.add(t);
            values.set(all).sort(comparator);
            branches.clear();
        }

        addBranches(new BinTree<>(comparator), new BinTree<>(comparator));

        T key = key();
        for(T value : values){
            if(comparator.get(value) < comparator.get(key)) left().add(value);
            else if(comparator.get(value) > comparator.get(key)) right().add(value);
        }
        left().format();
        right().format();

        values.clear().add(key);

        return this;
    }

    public T find(Floatf<T> conditions){
        if(!formatted) format();

        if(zero(conditions.get(key()))) return key();

        if(branches.size > 0){
            if(conditions.get(key()) < 0) return left().find(conditions);
            if(conditions.get(key()) > 0) return right().find(conditions);
        }

        return null;
    }
}
