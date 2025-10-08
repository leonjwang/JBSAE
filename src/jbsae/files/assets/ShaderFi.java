package jbsae.files.assets;

import jbsae.*;
import jbsae.graphics.gl.*;

import java.io.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Stringf.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderFi extends AssetFi{
    public Shader shader;

    public ShaderFi(String path){
        super(path);
    }

    @Override
    public ShaderFi gen(){
        Log.info("Generating shader: " + path());
        try(BufferedReader reader = reader()){
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) builder.append(line).append("\n");
            assets.shaders.add(name, shader);
            int type = path.endsWith(".frag") ? GL_FRAGMENT_SHADER : path.endsWith(".vert") ? GL_VERTEX_SHADER : -1;
            shader = new Shader(builder.toString(), type);
        }catch(Exception e){
            Log.error("Failed to load shader: " + path());
            Log.error(getStackTrace(e));
        }
        return (ShaderFi)super.gen();
    }
}
