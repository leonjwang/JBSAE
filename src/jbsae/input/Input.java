package jbsae.input;

import static jbsae.JBSAE.*;
import static org.lwjgl.glfw.GLFW.*;

public class Input{
    public KeyInput keyInput = new KeyInput();

    public Input(){
    }

    public void init(){
        glfwSetKeyCallback(window.id, keyInput);
    }

    public void dispose(){
        keyInput.free();
    }
}
