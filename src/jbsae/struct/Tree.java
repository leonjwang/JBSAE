package jbsae.struct;

import jbsae.struct.tree.*;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

// TODO: This and the quad/rangetrees might have to be rewritten
public class Tree<T> implements Iterable<T>{
    public Seq<T> values;
    public Tree parent;
    public Seq<Tree> branches;


    public Tree(){
        branches = new Seq<>();
        values = new Seq<>();
    }


    public Tree<T> add(T value){
        values.add(value);
        return this;
    }

    public Tree<T> addAll(T... arr){
        for(T value : arr) add(value);
        return this;
    }

    public Tree<T> addAll(Seq<T> arr){
        for(T value : arr) add(value);
        return this;
    }

    public Tree<T> addBranch(Tree tree){
        if(tree == null) return this;
        tree.parent = this;
        branches.add(tree);
        return this;
    }

    public Tree<T> addBranches(Tree... trees){
        for(Tree t : trees) addBranch(t);
        return this;
    }

    public Tree<T> remove(T value){
        values.remove(value);
        return this;
    }


    public boolean contains(T value){
        for(T t : this) if(eql(t, value)) return true;
        return false;
    }

    public int size(){
        int res = values.size;
        for(Tree t : branches) res += t.size();
        return res;
    }

    public int depth(){
        if(branches.size == 0) return 0;

        int max = 0;
        for(Tree t : branches) max = max(max, t.depth());
        return 1 + max;
    }

    public Tree<T> clear(){
        parent = null;
        values.clear();
        for(Tree t : branches) t.clear();
        branches.clear();
        return this;
    }


    @Override
    public Iterator<T> iterator(){
        return new TreeIterator();
    }

    @Override
    public String toString(){
        return values.toString();
    }

    private class TreeIterator implements Iterator<T>{
        public Seq<TreeIterator> iterators = new Seq<>();
        public int branch, index;

        public TreeIterator(){
            for(Tree t : branches) iterators.add((TreeIterator)t.iterator());
        }

        @Override
        public boolean hasNext(){
            if(index < values.size) return true;
            if(branch >= iterators.size) return false;
            if(!iterators.get(branch).hasNext()){
                branch++;
                return hasNext();
            }
            return iterators.get(branch).hasNext();
        }

        @Override
        public T next(){
            if(index >= values.size) return iterators.get(branch).next();
            return values.get(index++);
        }
    }
}
