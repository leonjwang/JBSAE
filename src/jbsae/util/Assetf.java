package jbsae.util;

import java.io.*;

public class Assetf{
    public static byte[] bytes(InputStream input){
        try{
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int read;
            byte[] data = new byte[4];

            while((read = input.read(data, 0, data.length)) != -1) buffer.write(data, 0, read);

            buffer.flush();
            return buffer.toByteArray();
        }catch(IOException e){
            System.out.println("Failed to convert input to bytes: " + input.toString());
            e.printStackTrace();
            return null;
        }
    }
}
