package jbsae.graphics.gl;

import jbsae.files.assets.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

/** @author Heiko Brumme */
public class Window{
    public long id;
    public IntBuffer widthBuffer, heightBuffer;

    public static GLFWErrorCallback errorCallback;

    public Window(){
    }

    public void init(){
        errorCallback = GLFWErrorCallback.createPrint(System.err);
        glfwSetErrorCallback(errorCallback);

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 4);
        id = glfwCreateWindow(width, height, programName, NULL, NULL);
        widthBuffer = MemoryUtil.memAllocInt(1);
        heightBuffer = MemoryUtil.memAllocInt(1);

        if(id == NULL) glfwTerminate();

        glfwMakeContextCurrent(id);
        GL.createCapabilities();

        Texture icon = new TextureFi("assets/" + programName + ".png").load().texture;
        GLFWImage image = GLFWImage.malloc();
        image.set(icon.width, icon.height, icon.image);
        glfwSetWindowIcon(id, GLFWImage.malloc(1).put(0, image));
    }

    public void update(){
        glfwGetFramebufferSize(id, widthBuffer, heightBuffer);
        curWidth = widthBuffer.get();
        curHeight = heightBuffer.get();
        widthBuffer.rewind();
        heightBuffer.rewind();
        glViewport(0, 0, curWidth, curHeight);
    }

    public void dispose(){
        glfwDestroyWindow(id);
        errorCallback.free();
    }

    public boolean keep(){
        return !glfwWindowShouldClose(id);
    }

    public void swap(){
        glfwSwapBuffers(id);
    }

    public void poll(){
        glfwPollEvents();
    }
}
