package jbsae.files.assets;

import jbsae.files.*;
import jbsae.struct.*;

import java.io.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.opengl.GL20.*;

public class AssetFi extends Fi{
    public String path, name, parent;

    public boolean loaded = false;

    public AssetFi(String path){
        this.path = path;
        this.name = path.substring(path.lastIndexOf("/") + 1);
        this.parent = path.substring(0, path.lastIndexOf("/"));
        assets.files.add(path(), this);
    }

    @Override
    public String name(){
        return name;
    }

    @Override
    public String path(){
        return path;
    }

    @Override
    public String parent(){
        return parent;
    }

    @Override
    public InputStream input(){
        try{
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path.substring(assets.assetsFolder.length() + 1));
            if(stream == null){
                this.file = new File(path);
                stream = super.input();
            }
            return stream;
        }catch(NullPointerException e){
            System.out.println("Failed reading file: " + path);
            e.printStackTrace();
            return null;
        }
    }

    public AssetFi load(){
        return loaded ? this : gen();
    }

    public AssetFi gen(){
        this.loaded = true;
        return this;
    }
}
