package jbsae.input;


import org.lwjgl.glfw.*;

import static jbsae.JBSAE.*;

public class PosInput extends GLFWCursorPosCallback{
    @Override
    public void invoke(long id, double x, double y){
        input.pmouse.set(input.mouse);
        input.mouse.set((float)x, curHeight - (float)y).scl((float)width / curWidth, (float)height / curHeight);
    }
}
