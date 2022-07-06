package jbsae.files.assets;

import jbsae.files.*;
import jbsae.struct.*;

import java.io.*;

import static org.lwjgl.opengl.GL20.*;

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
            else if(file.getName().endsWith(".fnt")) arr.add(new FontFi(file));
            else if(file.getName().endsWith(".frag")) arr.add(new ShaderFi(file, GL_FRAGMENT_SHADER));
            else if(file.getName().endsWith(".vert")) arr.add(new ShaderFi(file, GL_VERTEX_SHADER));
            else if(file.getName().endsWith(".ogg")) arr.add(new SoundFi(file));
            else if(file.getName().endsWith(".png")) arr.add(new TextureFi(file));
        }
        return arr;
    }
}
