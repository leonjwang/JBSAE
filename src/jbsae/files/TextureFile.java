package jbsae.files;

import jbsae.graphics.gl.*;
import jbsae.struct.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.stb.STBImage.*;

public class TextureFile extends AssetFile{
    public static Seq<TextureFile> all = new Seq<>();

    public Texture texture;

    public TextureFile(String name){
        super(name);
        all.add(this);
    }

    @Override
    public TextureFile load(){
        return (TextureFile)super.load();
    }

    @Override
    public TextureFile create(){
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
