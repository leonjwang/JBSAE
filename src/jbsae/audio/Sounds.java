package jbsae.audio;

import jbsae.math.*;
import jbsae.struct.*;
import org.lwjgl.openal.*;

import java.nio.*;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Sounds{
    public long device, context;

    public SoundListener listener;

    public Seq<SoundBuffer> soundBufferList;

    public Map<String, SoundSource> soundSourceMap;

    public Mat4 cameraMatrix;

    public Sounds(){
        soundBufferList = new Seq<>();
        soundSourceMap = new Map<>();
        cameraMatrix = new Mat4();
    }

    public void init(){
        this.device = alcOpenDevice(alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER));
        this.context = alcCreateContext(device, new int[]{0});
        if(context == NULL) throw new IllegalStateException("Failed to create OpenAL context.");
        alcMakeContextCurrent(context);
        AL.createCapabilities(ALC.createCapabilities(device));

        listener = new SoundListener();
    }

    public void addSoundSource(String name, SoundSource soundSource){
        soundSourceMap.add(name, soundSource);
    }

    public void playSoundSource(String name){
        SoundSource soundSource = soundSourceMap.get(name);
        if(soundSource != null){//!soundSource.isPlaying()){
            soundSource.play();
        }
    }

    public void removeSoundSource(String name){
        this.soundSourceMap.remove(name);
    }

    public void addSoundBuffer(SoundBuffer soundBuffer){
        this.soundBufferList.add(soundBuffer);
    }

    public void setListener(SoundListener listener){
        this.listener = listener;
    }

    public void setAttenuationModel(int model){
        alDistanceModel(model);
    }

    public void dispose(){
        for(Object soundSource : soundSourceMap.values()){
            ((SoundSource)soundSource).cleanup();
        }
        soundSourceMap.clear();
        for(SoundBuffer soundBuffer : soundBufferList){
            soundBuffer.cleanup();
        }
        soundBufferList.clear();
        if(context != NULL){
            alcDestroyContext(context);
        }
        if(device != NULL){
            alcCloseDevice(device);
        }
    }
}

