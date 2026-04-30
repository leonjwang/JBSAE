package jbsae.struct;

import java.util.*;

public interface Listable<T> extends Iterable<T>{
    default Object[] list(){
        int i = 0;
        Object[] list = new Object[size()];
        for(T val : this) list[i++] = val;
        return list;
    }

    int size();

    @Override
    Listerator<T> iterator();

    public abstract interface Listerator<T> extends Iterator<T>{
        int size();
    }
}
