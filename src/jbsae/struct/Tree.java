package jbsae.struct;

import java.util.*;

import static jbsae.util.Structf.*;

public class Tree<T> implements Iterable<T>{
    public Seq<T> values;
    public Tree parent;
    public Seq<Tree> branches;

    public Tree(){
        branches = new Seq<>();
        values = new Seq<>();
    }

    public void add(T value){
        values.add(value);
    }

    public void addAll(T... arr){
        for(T value : arr) add(value);
    }

    public void addBranch(Tree tree){
        if(tree == null) return;
        tree.parent = this;
        branches.add(tree);
    }

    public void addBranches(Tree... trees){
        for(Tree t : trees) addBranch(t);
    }

    public void remove(T value){
        values.remove(value);
    }

    public void removeAll(T... values){
        this.values.removeAll(values);
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

    @Override
    public Iterator<T> iterator(){
        return new TreeIterator();
    }

    private class TreeIterator implements Iterator<T>{
        public Seq<TreeIterator> biterators = new Seq<>();
        public int bindex, index;

        public TreeIterator(){
            for(Tree t : branches) biterators.add((TreeIterator)t.iterator());
        }

        @Override
        public boolean hasNext(){
            if(index < values.size) return true;
            if(bindex >= biterators.size) return false;
            if(!biterators.get(bindex).hasNext()){
                bindex++;
                return hasNext();
            }
            return biterators.get(bindex).hasNext();
        }

        @Override
        public T next(){
            if(index >= values.size) return biterators.get(bindex).next();
            return values.get(index++);
        }
    }
}
