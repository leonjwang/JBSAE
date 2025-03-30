package jbsae.input;

import org.lwjgl.glfw.*;

import static jbsae.JBSAE.*;

public class ButtonInput extends GLFWMouseButtonCallback{
    @Override
    public void invoke(long id, int button, int pressed, int arg){
        if(pressed == 1){
            if(button == 0) input.clicking[0] = true;
            else if(button == 1) input.clicking[1] = true;
            else if(button == 2) input.clicking[2] = true;
        }else{
            if(button == 0) input.clicking[0] = false;
            else if(button == 1) input.clicking[1] = false;
            else if(button == 2) input.clicking[2] = false;
        }
    }
}
