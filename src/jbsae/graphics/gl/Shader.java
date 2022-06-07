package jbsae.graphics.gl;

import jbsae.files.*;
import jbsae.struct.*;

import java.io.*;

import static org.lwjgl.opengl.GL20.*;

/** @author Heiko Brumme */
public class Shader extends Asset{
    public static Seq<Shader> all = new Seq<>();

    public int id, type;

    public Shader(String name, int type){
        super(name);
        this.type = type;
        all.add(this);
    }

    @Override
    public Shader create(){
        super.create();

        id = glCreateShader(type);
        StringBuilder builder = new StringBuilder();
        try{
            String line;
            BufferedReader reader =  reader();
            while((line = reader.readLine()) != null) builder.append(line).append("\n");
        }catch(Exception e){
            System.out.println("Failed to load shader: " + path());
            e.printStackTrace();
        }
        glShaderSource(id, builder.toString());
        glCompileShader(id);
        glUseProgram(id);
        return this;
    }

    public void dispose(){
        glDeleteShader(id);
    }
}
