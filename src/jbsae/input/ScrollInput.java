package jbsae.input;


import org.lwjgl.glfw.*;

import static jbsae.JBSAE.*;

public class ScrollInput extends GLFWScrollCallback{
    @Override
    public void invoke(long id, double b, double direction){
        input.scroll += direction;
    }
}
