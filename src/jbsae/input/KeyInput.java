package jbsae.input;

import jbsae.*;
import org.lwjgl.glfw.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.glfw.GLFW.*;

public class KeyInput extends GLFWKeyCallback{
    public void invoke(long id, int key, int scancode, int action, int mods){
        Log.trace("Key " + key + (action == GLFW_PRESS ? " pressed" : action == GLFW_RELEASE ? " released" : " repeated"));

        if(key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) glfwSetWindowShouldClose(id, true);

        if(action == GLFW_PRESS || action == GLFW_REPEAT) input.pressed.add(key);
        else input.pressed.remove(key);
    }
}