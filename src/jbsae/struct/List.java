package jbsae.struct;

import jbsae.func.*;

public interface List<T> extends Iterable<T>{
    Object[] list();

    T get(int index);

    List<T> set(int index, T value);

    int size();
}
