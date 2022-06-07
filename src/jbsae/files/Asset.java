package jbsae.files;

import jbsae.struct.*;

public class Asset extends Fi{
    public static Seq<Asset> all = new Seq<>();

    public boolean loaded;

    public Asset(String name){
        super(name);
        all.add(this);
    }

    public Asset load(){
        if(!loaded) create();
        return this;
    }

    public Asset create(){
        loaded = true;
        return this;
    }

    @Override
    public int hashCode(){
        return path().hashCode();
    }
}
