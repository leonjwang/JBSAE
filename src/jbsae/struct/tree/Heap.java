package jbsae.struct.tree;

import jbsae.func.prim.*;
import jbsae.struct.*;

public class Heap<T>{
    private Seq<T> values;

    public boolean min = true;

    public Floatf<T> value;

    public Heap(Floatf<T> value){
        this.value = value;
        this.values = new Seq<T>().ordered(true);
    }

    public Heap<T> min(boolean min){
        this.min = min;
        return this;
    }

    public Seq<T> values(){
        return new Seq<T>(size()).addAll(values);
    }

    public Heap<T> push(T value){
        values.add(value);
        swim(size() - 1);
        return this;
    }

    public Heap<T> pushAll(List<T> values){
        int i = size();
        this.values.addAll(values);
        for(;i < size();i++) swim(i);
        return this;
    }

    private void swim(int index){
        if(index == 0) return;
        int parent = (index - 1) / 2;
        if(above(index, parent)){
            swap(index, parent);
            swim(parent);
        }
    }

    private void sink(int index){
        int left = index * 2 + 1, right = left + 1;
        if(left >= size()) return;
        int smaller = right >= size() ? left : (above(left, right) ? left : right);
        if(above(smaller, index)){
            swap(smaller, index);
            sink(smaller);
        }
    }

    private boolean above(int a, int b){
        boolean smaller = value.get(values.get(a)) < value.get(values.get(b));
        return min ? smaller : !smaller;
    }

    private void swap(int a, int b){
        T tmp = values.get(a);
        values.set(a, values.get(b)).set(b, tmp);
    }


    public T pop(){
        T value = values.get(0);
        swap(0, values.size() - 1);
        values.remove(size() - 1);
        sink(0);
        return value;
    }

    public T peek(){
        return values.get(0);
    }

    public Heap<T> ensure(int space){
        values.ensure(space);
        return this;
    }

    public int size(){
        return values.size();
    }
}
