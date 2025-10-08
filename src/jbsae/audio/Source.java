package jbsae.audio;

import jbsae.math.*;
import jbsae.struct.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.openal.AL10.*;

// TODO: Sound files kinda ignore position right now
public class Source{
    public int id;

    public Source(Sound sound){
        sounds.sources.add(this);

        id = alGenSources();
        set(sound);
    }

    public Source set(Sound sound){
        stop();
        alSourcei(id, AL_BUFFER, sound.id);
        return this;
    }

    public Source loop(boolean loop){
        alSourcei(id, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
        return this;
    }

    public Source relative(boolean relative){
        alSourcei(id, AL_SOURCE_RELATIVE, relative ? AL_TRUE : AL_FALSE);
        return this;
    }

    public Source position(Vec3 position){
        alSource3f(id, AL_POSITION, position.x, position.y, position.z);
        return this;
    }

    public Source speed(Vec3 speed){
        alSource3f(id, AL_VELOCITY, speed.x, speed.y, speed.z);
        return this;
    }

    public Source gain(float gain){
        alSourcef(id, AL_GAIN, gain);
        return this;
    }

    public Source pitch(float pitch){
        alSourcef(id, AL_PITCH, pitch);
        return this;
    }


    public void play(){
        alSourcePlay(id);
    }

    public void pause(){
        alSourcePause(id);
    }

    public void stop(){
        alSourceStop(id);
    }

    public boolean playing(){
        return alGetSourcei(id, AL_SOURCE_STATE) == AL_PLAYING;
    }

    public void dispose(){
        stop();
        alDeleteSources(id);
    }
}