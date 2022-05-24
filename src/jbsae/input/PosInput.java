package jbsae.input;


import org.lwjgl.glfw.*;

import static jbsae.JBSAE.*;

public class PosInput extends GLFWCursorPosCallback{
    @Override
    public void invoke(long id, double x, double y){
        input.pmouse.set(input.mouse);
        input.mouse.set((float)x, curHeight / 2f - (float)y).scl(2f * width / curWidth, 2f * height / curHeight);
    }
}
