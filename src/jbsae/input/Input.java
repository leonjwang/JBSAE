package jbsae.input;

import jbsae.math.*;
import jbsae.struct.prim.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.glfw.GLFW.*;

public class Input{
    public IntSet pressed = new IntSet();
    public Vec2 mouse = new Vec2(0, 0);
    public Vec2 pmouse = new Vec2(0, 0);
    public boolean[] clicking = new boolean[]{false, false, false};
    public int scroll = 0;

    public KeyInput keyInput = new KeyInput();
    public PosInput posInput = new PosInput();
    public ButtonInput buttonInput = new ButtonInput();
    public ScrollInput scrollInput = new ScrollInput();

    public Input(){
    }

    public void init(){
        glfwSetKeyCallback(window.id, keyInput);
        glfwSetCursorPosCallback(window.id, posInput);
        glfwSetMouseButtonCallback(window.id, buttonInput);
        glfwSetScrollCallback(window.id, scrollInput);
    }

    public void dispose(){
        keyInput.free();
    }
}
