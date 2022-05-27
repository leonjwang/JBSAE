package jbsae.audio;

import jbsae.math.*;

import static org.lwjgl.openal.AL10.*;

public class SoundListener{
    public SoundListener(){
        this(new Vec3());
    }

    public SoundListener(Vec3 position){
        alListener3f(AL_POSITION, position.x, position.y, position.z);
        alListener3f(AL_VELOCITY, 0, 0, 0);
    }

    public void setSpeed(Vec3 speed){
        alListener3f(AL_VELOCITY, speed.x, speed.y, speed.z);
    }

    public void setPosition(Vec3 position){
        alListener3f(AL_POSITION, position.x, position.y, position.z);
    }

    public void setOrientation(Vec3 at, Vec3 up){
        float[] data = new float[6];
        data[0] = at.x;
        data[1] = at.y;
        data[2] = at.z;
        data[3] = up.x;
        data[4] = up.y;
        data[5] = up.z;
        alListenerfv(AL_ORIENTATION, data);
    }
}
