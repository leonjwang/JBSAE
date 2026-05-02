package jbsae.struct.prim.iterator;

public abstract class CharIterator{
    public int index = 0;

    public abstract boolean hasNext();

    public abstract char next();
}
