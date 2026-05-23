package jbsae.struct.prim.iterator;

import java.util.*;

public abstract class FloatIterator implements Iterator<Float>{
    public abstract boolean hasNext();

    public abstract float nextf();

    public Float next(){
        return nextf();
    }
}
