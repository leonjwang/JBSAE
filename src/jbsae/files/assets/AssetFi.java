package jbsae.files.assets;

import jbsae.files.*;
import jbsae.struct.*;

import java.io.*;

public class AssetFi extends Fi{
    public static Map<String, AssetFi> all = new Map<>(); //If you have more than one asset with the same exact name, I declare you have skill issue

    public boolean loaded;

    public AssetFi(String name){
        super(name);
        all.add(name(), this);
    }

    public AssetFi(File file){
        super(file);
        all.add(name(), this);
    }

    public AssetFi load(){
        if(!loaded) create();
        return this;
    }

    public AssetFi create(){
        loaded = true;
        return this;
    }
}
