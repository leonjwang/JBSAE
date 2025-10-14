package jbsae.audio;

import jbsae.*;
import jbsae.struct.*;
import org.lwjgl.openal.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.AL11.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Sounds{
    public long device, context;

    public Listener listener;

    public Seq<Source> buffer = new Seq<>();
    public Seq<Source> sources = new Seq<>();

//    public Mat4 cameraMatrix;

    public Sounds(){
    }

    public void init(){
        this.device = alcOpenDevice(alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER));
        this.context = alcCreateContext(device, new int[]{0});

        alcMakeContextCurrent(context);
        AL.createCapabilities(ALC.createCapabilities(device));
        alDistanceModel(AL_EXPONENT_DISTANCE);

        listener = new Listener();
    }

    public void update(){
        buffer.clear();
        for(Source source : sources){
            if(source.playing()) buffer.add(source);
            else source.dispose();
        }
        Seq<Source> tmp = sources;
        sources = buffer;
        buffer = tmp;
    }

    public Source play(String name){
        return assets.sounds.get(name).play();
    }

    public void dispose(){
        for(Source source : sources) source.dispose();

        if(context != NULL) alcDestroyContext(context);
        if(device != NULL) alcCloseDevice(device);
    }
}

