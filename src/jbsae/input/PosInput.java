package jbsae.input;


import org.lwjgl.glfw.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

public class PosInput extends GLFWCursorPosCallback {
    @Override
    public void invoke(long window, double x, double y) {
        input.pmouse.set(input.mouse);

        // Get the current window size in logical units
        int[] winWidth = new int[1];
        int[] winHeight = new int[1];
        glfwGetWindowSize(window, winWidth, winHeight);

        // Scale cursor from window coordinates → framebuffer coordinates
        double mouseX = x * ((double) curWidth / winWidth[0]);
        double mouseY = (winHeight[0] - y) * ((double) curHeight / winHeight[0]); // flip Y

        // Map to your game's logical width/height
        input.mouse.set((float) mouseX, (float) mouseY)
        .scl((float) width / curWidth, (float) height / curHeight);
    }
}