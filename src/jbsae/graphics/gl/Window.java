package jbsae.graphics.gl;

import jbsae.*;
import jbsae.util.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Window{
    public long id;
    public IntBuffer widthBuffer, heightBuffer;

    // Dynamically updated
    public static int winWidth = 1, winHeight = 1;
    public static int frameWidth = 1, frameHeight = 1;

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
    }

    public void icon(Texture icon){
        if(Platform.get() != Platform.MACOSX){
            Log.info("Setting window icon");
            GLFWImage image = GLFWImage.malloc();
            image.set(icon.width, icon.height, icon.image);
            glfwSetWindowIcon(id, GLFWImage.malloc(1).put(0, image));
        }else Log.error("MacOS icons should be managed outside of the program");
    }

    public void update(){
        glfwGetWindowSize(id, widthBuffer, heightBuffer);
        winWidth = Mathf.max(widthBuffer.get(), 1);
        winHeight = Mathf.max(heightBuffer.get(), 1);
        widthBuffer.rewind();
        heightBuffer.rewind();

        glfwGetFramebufferSize(id, widthBuffer, heightBuffer);
        frameWidth = Mathf.max(widthBuffer.get(), 1);
        frameHeight = Mathf.max(heightBuffer.get(), 1);
        widthBuffer.rewind();
        heightBuffer.rewind();
        glViewport(0, 0, frameWidth, frameHeight);
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
