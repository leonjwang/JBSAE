package jbsae.audio;

import jbsae.struct.*;
import org.lwjgl.stb.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.openal.AL10.*;

public class Sound{
    public static Seq<Sound> all = new Seq<>();

    public int id;

    public ShortBuffer pcm;
    public STBVorbisInfo info;

    public Sound(ShortBuffer pcm, STBVorbisInfo info){
        all.add(this);

        id = alGenBuffers();
        this.pcm = pcm;
        this.info = info;
        alBufferData(id, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
    }

    public void dispose(){
        all.remove(this);
        alDeleteBuffers(id);
        if(pcm != null) MemoryUtil.memFree(pcm);
    }
}