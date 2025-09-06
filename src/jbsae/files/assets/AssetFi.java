package jbsae.files.assets;

import jbsae.*;
import jbsae.files.*;

import java.io.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Stringf.*;

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
            Log.error("Failed getting input stream to asset: " + path);
            Log.error(getStackTrace(e));
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
