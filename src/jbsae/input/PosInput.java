package jbsae.input;


import jbsae.*;
import org.lwjgl.glfw.*;

import static jbsae.JBSAE.*;

public class PosInput extends GLFWCursorPosCallback{
    @Override
    public void invoke(long id, double x, double y){
        Log.trace("Mouse moved to " + x + ", " + y);

        input.pmouse.set(input.mouse);

        double mouseX = x * ((double)window.frameWidth / window.winWidth);
        double mouseY = (window.winHeight - y) * ((double)window.frameHeight / window.winHeight);

        input.mouse.set((float)mouseX, (float)mouseY).scl((float)width / window.frameWidth, (float)height / window.frameHeight);
    }
}