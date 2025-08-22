package jbsae.files.assets;

import jbsae.files.*;
import jbsae.struct.*;

import java.io.*;

public class AssetDir extends Fi{
    public AssetDir(String name){
        super(name);
    }

    public AssetDir(File file){
        super(file);
    }

    public Seq<AssetFi> list(Seq<AssetFi> arr){
        File[] contents = file.listFiles();
        for(File file : contents){
            if(file.isDirectory()) new AssetDir(file).list(arr);
            else arr.add(AssetFi.create(file.getPath()));
        }
        return arr;
    }
}
