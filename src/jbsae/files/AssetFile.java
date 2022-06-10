package jbsae.files;

public class AssetFile extends Fi{
    public boolean loaded;

    public AssetFile(String name){
        super(name);
    }

    public AssetFile load(){
        if(!loaded) create();
        return this;
    }

    public AssetFile create(){
        loaded = true;
        return this;
    }

    @Override
    public int hashCode(){
        return path().hashCode();
    }
}
