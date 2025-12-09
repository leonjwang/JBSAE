package jbsae.struct.tree;

import jbsae.func.prim.*;
import jbsae.struct.*;

public class MinHeap<T>{
    public Floatf<T> comparator;

    public Seq<T> values;

    public MinHeap(Floatf<T> comparator){
        this.comparator = comparator;
    }

    public MinHeap(Floatf<T> comparator, List<T> values){
        this.comparator = comparator;
        this.values = new Seq<>(values);
        balance();
    }

    public T get(int index){
        return values.get(index);
    }

    public MinHeap<T> insert(T value){
        values.add(value);
        return swim(values.size() - 1);
    }

    public MinHeap<T> remove(int index){
        int n = size() - 1;
        swap(index, n);
        values.remove(n);
        if(index == n) return this;

        int parent = (index - 1) / 2;
        if(index > 0 && comparator.get(values.get(index)) < comparator.get(values.get(parent))) swim(index);
        else sink(index);
        return this;
    }

    public MinHeap<T> balance(){
        for(int i = (size() / 2) - 1;i >= 0;i--) sink(i);
        return this;
    }

    public int size(){
        return values.size;
    }

    private MinHeap<T> swim(int i){
        int parent = (i - 1) / 2;
        if(comparator.get(values.get(parent)) > comparator.get(values.get(i))) swap(parent, i).swim(parent);
        return this;
    }

    private MinHeap<T> sink(int i){
        int a = i * 2, b = i * 2 + 1;
        if(a >= size()) return this;
        if(b >= size()) return swap(i, a);

        if(comparator.get(values.get(a)) < comparator.get(values.get(b))) swap(i, a).sink(a);
        else swap(i, b).sink(b);
        return this;
    }

    public MinHeap<T> swap(int i, int j){
        T tmp = values.get(i);
        values.set(values.get(j), i).set(tmp, j);
        return this;
    }
}