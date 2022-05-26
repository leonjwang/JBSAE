package jbsae.graphics.gl;

import java.io.*;

import static org.lwjgl.opengl.GL20.*;

/** @author Heiko Brumme */
public class Shader{
    public int id;

    public Shader(String path, int type){
        id = glCreateShader(type);
        load(path);
    }

    public void load(String path){
        StringBuilder builder = new StringBuilder();
        try{
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            while((line = reader.readLine()) != null) builder.append(line).append("\n");
        }catch(Exception e){
            System.out.println("Failed to load shader: " + path);
            e.printStackTrace();
        }
        glShaderSource(id, builder.toString());
        glCompileShader(id);
        glUseProgram(id);
    }

    public void dispose(){
        glDeleteShader(id);
    }
}
