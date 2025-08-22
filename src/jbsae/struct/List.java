package jbsae.struct;

import jbsae.func.*;

public interface List<T> extends Iterable<T>{
    Object[] list();


    List<T> set(T value, int index);

    List<T> set(Object... values);

    List<T> set(List<T> values);

    List<T> each(Cons<T> values);


    T get(int index);

    int size();
}
