package jbsae.files.assets;

import jbsae.graphics.gl.*;
import org.lwjgl.system.*;

import java.io.*;
import java.nio.*;

import static org.lwjgl.stb.STBImage.*;

public class TextureFi extends AssetFi{
    public Texture texture;

    public TextureFi(String name){
        super(name);
    }

    public TextureFi(File file){
        super(file);
    }

    @Override
    public TextureFi load(){
        return (TextureFi)super.load();
    }

    @Override
    public TextureFi create(){
        super.create();

        try{
            MemoryStack stack = MemoryStack.stackPush();
            IntBuffer w = stack.mallocInt(1), h = stack.mallocInt(1);
            ByteBuffer image = stbi_load(path(), w, h, stack.mallocInt(1), 4);
            if(image == null) throw new Exception("Image is null!");

            texture = new Texture(w.get(), h.get(), image);
        }catch(Exception e){
            System.out.println("Failed to load texture: " + path());
            e.printStackTrace();
        }

        return this;
    }
}
