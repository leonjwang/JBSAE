package jbsae.input;

import org.lwjgl.glfw.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.glfw.GLFW.*;

public class KeyInput extends GLFWKeyCallback{
    public void invoke(long window, int key, int scancode, int action, int mods){
        if(key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) glfwSetWindowShouldClose(window, true);

        if(action == GLFW_PRESS || action == GLFW_REPEAT) input.pressed.add(key);
        else input.pressed.remove(key);
    }
}