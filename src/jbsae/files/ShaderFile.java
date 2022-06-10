package jbsae.files;

import jbsae.graphics.gl.*;
import jbsae.struct.*;

import java.io.*;

public class ShaderFile extends AssetFile{
    public static Seq<ShaderFile> all = new Seq<>();

    public int type;
    public Shader shader;

    public ShaderFile(String name, int type){
        super(name);
        this.type = type;
        all.add(this);
    }

    @Override
    public ShaderFile load(){
        return (ShaderFile)super.load();
    }

    @Override
    public ShaderFile create(){
        super.create();

        StringBuilder builder = new StringBuilder();
        try{
            String line;
            BufferedReader reader =  reader();
            while((line = reader.readLine()) != null) builder.append(line).append("\n");
        }catch(Exception e){
            System.out.println("Failed to load shader: " + path());
            e.printStackTrace();
        }
        shader = new Shader(builder.toString(), type);
        return this;
    }
}
