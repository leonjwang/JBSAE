package jbsae.graphics.gl;

import jbsae.math.*;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class ShaderProgram{
    public int id;

    public ShaderProgram(Shader vertexShader, Shader fragmentShader){
        id = glCreateProgram();
        glAttachShader(id, vertexShader.id);
        glAttachShader(id, fragmentShader.id);
    }

    public void setVertex(String name, int size, int stride, int offset){
        int attribute = glGetAttribLocation(id, name);
        glEnableVertexAttribArray(attribute);
        glVertexAttribPointer(attribute, size, GL_FLOAT, false, stride, offset);
    }

    public void setUniform(String name, int value){
        int uniform = glGetUniformLocation(id, name);
        glUniform1i(uniform, value);
    }

    public void setUniform(String name, Mat4 value){
        int uniform = glGetUniformLocation(id, name);
        glUniformMatrix4fv(uniform, false, value.buffer());
    }

    public void bindFragment(String name, int number){
        glBindFragDataLocation(id, number, name);
    }

    public void link(){
        glLinkProgram(id);
    }

    public void use(){
        glUseProgram(id);
    }

    public void dispose(){
        glDeleteProgram(id);
    }
}
