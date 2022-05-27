package jbsae.audio;

import java.io.*;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import jbsae.util.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;

import static org.lwjgl.openal.AL.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC11.*;
import static org.lwjgl.openal.ALC.*;

public class Sus{
    public static void main(String[] args) throws Exception {

        // some init stuff from the docs
        long device;
        long context;

        String deviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);

        device = alcOpenDevice(deviceName);
        context = alcCreateContext(device, new int[]{0});

        alcMakeContextCurrent(context);
        AL.createCapabilities(ALC.createCapabilities(device));

        alDistanceModel(AL_INVERSE_DISTANCE_CLAMPED);   // using inverse distance clamped model

        int source = alGenSources();    // create a source
        alSource3f(source, AL_POSITION, 0, 0, 0);   // set the source position to 0

        // source values to work with the distance model
        alSourcef(source, AL_ROLLOFF_FACTOR, 1f);   // the rolloff factor makes some changes to the gain curve: the higher the rolloff, the steeper the curve will be
        alSourcef(source, AL_REFERENCE_DISTANCE, 5f);   // the reference distance determines the distance between source and listener where the gain is exactly 1
        //alSourcef(source, AL_MAX_DISTANCE, 15f);      // after this distance between source and listener, the sound won't be attenuated anymore in the clamped models
        // I leave this value commented because it would be exactly what I don't want

        alSourcef(source, AL_GAIN, 1f);     // set the gain of the sound to 1
        alSourcei(source, AL_LOOPING, AL_TRUE); // loop the sound
        alSourcef(source, AL_PITCH, 1);

        // now let's load the audio file
        int audioBuffer = alGenBuffers();   // we'll store the buffer id of the sound in this variable
        // load the audio file to an AudioInputStream
        AudioInputStream stream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream("assets/sounds/Mixing Game Music with EDM.wav")));

        AudioFormat audioFormat = stream.getFormat();
        int format, sampleRate, bytesPerFrame, totalBytes;
        // OpenAL needs a ByteBuffer to fill its buffer with readable data, so now we need to convert the AudioInputStream to a ByteBuffer and some audio infos
        ByteBuffer data;

        format = AL_FORMAT_MONO16;  // for simplicity, I set the format to mono16 but there are some other ways to get the right format
        sampleRate = (int)audioFormat.getSampleRate() * 2;
        bytesPerFrame = audioFormat.getFrameSize();
        totalBytes = (int)stream.getFrameLength() * bytesPerFrame;

        data = BufferUtils.createByteBuffer(totalBytes);

        // read the stream and put the data in the ByteBuffer
        byte[] temp = new byte[totalBytes];
        int read = stream.read(temp, 0, totalBytes);
        data.clear();
        data.put(temp, 0, read);
        data.flip();    // flip the buffer, otherwise OpenAL won't be able to read it

        stream.close(); // close the stream, we don't need it anymore

        alBufferData(audioBuffer, format, data, sampleRate);    // now we can finally send the audio data to OpenAL

        alSourcei(source, AL_BUFFER, audioBuffer);  // tell the source to play this sound


        alListener3f(AL_POSITION, 0, 0, 0); // set the initial listener position to -20 on the x and 2 on the z to have a nice 3D audio transition between the channels


        alSourcePlay(source);   // play the sound

        int f = 0;
        while(true) {
//            alListener3f(AL_POSITION, 0, 0, 0, 0);
            float[] h = new float[] {Mathf.cos(f), Mathf.sin(f), 0, 0, 0, 1};
//            alListenerfv(AL_ORIENTATION, h);
            f++;
            Thread.sleep(10);
        }
        //TODO: Maybe implement this version?
    }
}
