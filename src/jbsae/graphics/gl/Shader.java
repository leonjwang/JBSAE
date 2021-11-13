package jbsae.graphics.gl;

import java.io.*;

import static jbsae.util.Stringf.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader{
    public int id;

    public Shader(String path, int type){
        id = glCreateShader(type);
        load(path);
    }

    public void load(String path){
        StringBuilder builder = new StringBuilder();
        try{
            InputStream in = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine()) != null) builder.append(line).append("\n");
        }catch(Exception e){
            printDebug("Failed to load shader: " + path);
        }
        glShaderSource(id, builder.toString());
        glCompileShader(id);
        glUseProgram(id);
    }

    public void dispose(){
        glDeleteShader(id);
    }
}
