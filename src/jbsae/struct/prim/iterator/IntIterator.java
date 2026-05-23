package jbsae.struct.prim.iterator;

import java.util.*;

public abstract class IntIterator implements Iterator<Integer>{
    public abstract boolean hasNext();

    public abstract int nexti();

    public Integer next(){
        return nexti();
    }
}
