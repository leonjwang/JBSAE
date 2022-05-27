package jbsae.audio;

import org.lwjgl.stb.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;

import static jbsae.util.Mathf.*;
import static org.lwjgl.BufferUtils.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.*;

public class SoundBuffer{
    public int id;

    public ShortBuffer pcm = null;

    public ByteBuffer vorbis = null;

    public SoundBuffer(String file) throws Exception{
        this.id = alGenBuffers();
        try(STBVorbisInfo info = STBVorbisInfo.malloc()){
            ShortBuffer pcm = readVorbis(file, info);

            alBufferData(id, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
        }
    }

    public void cleanup(){
        alDeleteBuffers(this.id);
        if(pcm != null) MemoryUtil.memFree(pcm);
    }

    private ShortBuffer readVorbis(String resource, STBVorbisInfo info) throws Exception{
        try(MemoryStack stack = MemoryStack.stackPush()){
            SeekableByteChannel fc = Files.newByteChannel(Paths.get(resource));
            vorbis = createByteBuffer((int)fc.size() + 1);
            while(fc.read(vorbis) != -1) ;
            vorbis.flip();

            IntBuffer error = stack.mallocInt(1);
            long decoder = stb_vorbis_open_memory(vorbis, error, null);
            if(decoder == NULL) throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));

            stb_vorbis_get_info(decoder, info);

            int channels = info.channels();

            int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);

            pcm = MemoryUtil.memAllocShort(lengthSamples);

            pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
            stb_vorbis_close(decoder);

            return pcm;
        }
    }
}