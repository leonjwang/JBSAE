package jbsae.files.assets;

import jbsae.audio.*;
import org.lwjgl.*;
import org.lwjgl.openal.*;

import javax.sound.sampled.*;
import java.io.*;
import java.net.*;
import java.nio.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Assetf.*;

// TODO: So we don't support ogg or mp3 files anymore? When did that happen?
public class SoundFi extends AssetFi{
    public Sound sound;

    public SoundFi(String path){
        super(path);
    }

    @Override
    public AudioInputStream input(){
        try{
            URL url = Thread.currentThread().getContextClassLoader().getResource(path.substring(assets.assetsFolder.length() + 1));
            if(url == null){
                this.file = new File(path);
                return AudioSystem.getAudioInputStream(this.file.toURL());
            }
            return AudioSystem.getAudioInputStream(url);
        }catch(Exception e){
            System.out.println("Failed reading file: " + path);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SoundFi gen(){
        try(AudioInputStream stream = input()){
            final int MONO = 1, STEREO = 2;

            AudioFormat format = stream.getFormat();
            if(format.isBigEndian()) throw new UnsupportedAudioFileException("Can't handle Big Endian formats yet");

            int openALFormat = -1;
            switch(format.getChannels()){
                case MONO:
                    switch(format.getSampleSizeInBits()){
                        case 8:
                            openALFormat = AL10.AL_FORMAT_MONO8;
                            break;
                        case 16:
                            openALFormat = AL10.AL_FORMAT_MONO16;
                            break;
                    }
                    break;
                case STEREO:
                    switch(format.getSampleSizeInBits()){
                        case 8:
                            openALFormat = AL10.AL_FORMAT_STEREO8;
                            break;
                        case 16:
                            openALFormat = AL10.AL_FORMAT_STEREO16;
                            break;
                    }
                    break;
            }

            byte[] b = bytes(stream);
            ByteBuffer data = BufferUtils.createByteBuffer(b.length).put(b);
            data.flip();

            sound = new Sound(data, openALFormat, format);
            assets.sounds.add(name, sound);
        }catch(Exception e){
            System.out.println("Failed to load sound: " + path());
            e.printStackTrace();
        }

        return (SoundFi)super.gen();
    }
}
