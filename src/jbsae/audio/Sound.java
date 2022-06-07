package jbsae.audio;

import jbsae.files.*;
import jbsae.struct.*;
import org.lwjgl.stb.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;

import static org.lwjgl.BufferUtils.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Sound extends Asset{
    public static Seq<Sound> all = new Seq<>();

    public int id;

    public ShortBuffer pcm;
    public ByteBuffer vorbis;

    public Sound(String name){
        super(name);
        all.add(this);
    }

    @Override
    public Sound create(){
        super.create();

        id = alGenBuffers();
        try{
            MemoryStack stack = MemoryStack.stackPush();
            SeekableByteChannel fc = Files.newByteChannel(Paths.get(path()));

            vorbis = createByteBuffer((int)fc.size() + 1);
            while(fc.read(vorbis) != -1) ;
            vorbis.flip();

            IntBuffer error = stack.mallocInt(1);
            long decoder = stb_vorbis_open_memory(vorbis, error, null);
            if(decoder == NULL) throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));

            STBVorbisInfo info = STBVorbisInfo.malloc();

            stb_vorbis_get_info(decoder, info);

            int channels = info.channels(), lengthSamples = stb_vorbis_stream_length_in_samples(decoder) * 2; //TODO: Sussy workaround, and may have many unintended consequences; I can't find any other way to load long songs tho.

            pcm = MemoryUtil.memAllocShort(lengthSamples);

            pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
            stb_vorbis_close(decoder);

            alBufferData(id, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
        }catch(Exception e){
            System.out.println("Failed to load sound: " + path());
            e.printStackTrace();
        }
        return this;
    }

    public void dispose(){
        alDeleteBuffers(this.id);
        if(pcm != null) MemoryUtil.memFree(pcm);
    }
}