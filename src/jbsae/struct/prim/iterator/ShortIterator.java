package jbsae.struct.prim.iterator;

public abstract class ShortIterator{
    public int index = 0;

    public abstract boolean hasNext();

    public abstract short next();
}
