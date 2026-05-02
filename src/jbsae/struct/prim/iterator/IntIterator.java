package jbsae.struct.prim.iterator;

public abstract class IntIterator{
    public int index = 0;

    public abstract boolean hasNext();

    public abstract int next();
}
