package jbsae.input;


import jbsae.*;
import org.lwjgl.glfw.*;

import static jbsae.JBSAE.*;

public class ScrollInput extends GLFWScrollCallback{
    @Override
    public void invoke(long id, double b, double direction){
        Log.trace("Mouse scrolled " + direction);

        input.scroll += direction;
    }
}
