package jbsae.util;

import jbsae.*;

import java.io.*;

import static jbsae.util.Stringf.*;

public class Filef{
    public static byte[] bytes(InputStream input){
        try{
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int read;
            byte[] data = new byte[4];

            while((read = input.read(data, 0, data.length)) != -1) buffer.write(data, 0, read);

            buffer.flush();
            return buffer.toByteArray();
        }catch(IOException e){
            Log.error("Failed to convert input to bytes: " + input.toString());
            Log.error(getStackTrace(e));
            return null;
        }
    }

    public static byte left(byte value){
        return (byte)((value >> 4) & (byte)0x0F);
    }

    public static byte right(byte value){
        return (byte)(value & 0x0F);
    }
}
