package jbsae.input;


import org.lwjgl.glfw.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

public class PosInput extends GLFWCursorPosCallback{
    @Override
    public void invoke(long window, double x, double y){
        input.pmouse.set(input.mouse);

        double mouseX = x * ((double)frameWidth / winWidth);
        double mouseY = (winHeight - y) * ((double)frameHeight / winHeight);

        input.mouse.set((float)mouseX, (float)mouseY).scl((float)width / frameWidth, (float)height / frameHeight);
    }
}