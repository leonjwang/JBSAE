package jbsae.files.assets;

import jbsae.graphics.gl.*;

import java.io.*;

public class ShaderFi extends AssetFi{
    public int type;
    public Shader shader;

    public ShaderFi(String name, int type){
        super(name);
        this.type = type;
    }

    @Override
    public ShaderFi gen(){
        StringBuilder builder = new StringBuilder();
        try{
            String line;
            BufferedReader reader = reader();
            while((line = reader.readLine()) != null) builder.append(line).append("\n");
        }catch(Exception e){
            System.out.println("Failed to load shader: " + path());
            e.printStackTrace();
        }
        shader = new Shader(builder.toString(), type);
        return (ShaderFi)super.gen();
    }
}
