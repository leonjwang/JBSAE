package jbsae.util;

import java.lang.reflect.*;

@SuppressWarnings("all")
public class Structf{
    public static <T> T[] create(int size){
        return (T[])new Object[size];
    }

    public static <T> T[] create(int size, T[] items){
        return (T[])(items.getClass() == Object[].class ? new Object[size] :
            Array.newInstance(items.getClass().getComponentType(), size));
    }
}
