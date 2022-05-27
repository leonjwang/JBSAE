package jbsae.graphics.gl;

import jbsae.files.*;

import java.io.*;

import static org.lwjgl.opengl.GL20.*;

/** @author Heiko Brumme */
public class Shader{
    public int id;

    public Shader(Fi file, int type){
        id = glCreateShader(type);
        load(file);
    }

    public void load(Fi file){
        StringBuilder builder = new StringBuilder();
        try{
            String line;
            BufferedReader reader =  file.reader();
            while((line = reader.readLine()) != null) builder.append(line).append("\n");
        }catch(Exception e){
            System.out.println("Failed to load shader: " + file.path());
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
