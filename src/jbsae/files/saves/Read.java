package jbsae.files.saves;

import jbsae.*;
import jbsae.files.*;

import java.io.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;

public class Read{ //Consider combining with write
    public Fi file;
    public InputStream stream;

    public Read(Fi file){
        this.file = file;
        this.stream = file.input();
    }

    public int u(){
        try{
            return stream.read();
        }catch(IOException e){
            Log.error("Failed reading byte from file: " + file.path());
            Log.error(getStackTrace(e));
            return -1;
        }
    }

    public byte b(){
        return (byte)u();
    }

    public short s(){
        return (short)((u() << 8) | u());
    }

    public char c(){
        return (char)s();
    }

    public int i(){
        return ((int)s() << 16) | (u() << 8) | u();
    }

    public long l(){
        return ((long)i() << 32) | ((long)u() << 24) | ((long)u() << 16) | ((long)u() << 8) | u();
    }

    public float f(){
        return bitFloat(i());
    }

    public double d(){
        return bitDouble(l());
    }

    public String str(){
        int len = i();
        try{
            byte[] bytes = new byte[len];
            stream.read(bytes);
            return new String(bytes);
        }catch(IOException e){
            Log.error("Failed reading string from file: " + file.path());
            Log.error(getStackTrace(e));
        }
        return null;
    }

    public void close(){
        try{
            stream.close();
        }catch(IOException e){
            Log.error("Failed closing input stream to file: " + file.path());
            Log.error(getStackTrace(e));
        }
    }
}
