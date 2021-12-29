package jbsae.input;

import org.lwjgl.glfw.*;

import static jbsae.JBSAE.*;

public class ButtonInput extends GLFWMouseButtonCallback{
    @Override
    public void invoke(long id, int button, int pressed, int arg){
        input.clicking[0] = pressed == 1 && button == 0;
        input.clicking[1] = pressed == 1 && button == 1;
        input.clicking[2] = pressed == 1 && button == 2;
    }
}
