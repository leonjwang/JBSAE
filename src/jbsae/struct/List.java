package jbsae.struct;

import jbsae.func.*;

public interface List<T> extends Iterable<T>{
    public Object[] list();


    public List<T> set(T value, int index);

    public List<T> set(Object... values);

    public List<T> set(List<T> values);

    public List<T> each(Cons<T> values);


    public T get(int index);

    public int size();
}
