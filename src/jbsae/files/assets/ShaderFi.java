package jbsae.files.assets;

import jbsae.graphics.gl.*;

import java.io.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderFi extends AssetFi{
    public Shader shader;

    public ShaderFi(String path){
        super(path);
    }

    @Override
    public ShaderFi gen(){
        try(BufferedReader reader = reader()){
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) builder.append(line).append("\n");
            assets.shaders.add(name, shader);
            int type = path.endsWith(".frag") ? GL_FRAGMENT_SHADER : path.endsWith(".vert") ? GL_VERTEX_SHADER : -1;
            shader = new Shader(builder.toString(), type);
        }catch(Exception e){
            System.out.println("Failed to load shader: " + path());
            e.printStackTrace();
        }
        return (ShaderFi)super.gen();
    }
}
