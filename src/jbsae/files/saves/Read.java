package jbsae.files.saves;

import jbsae.files.*;

import java.io.*;

import static jbsae.util.Mathf.*;

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
            System.out.println("Failed reading file: " + file.path());
            e.printStackTrace();
            return -1;
        }
    }

    public byte b(){
        return (byte)u();
    }

    public short s(){
        return (short)((u() << 8) | u());
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

    public void close(){
        try{
            stream.close();
        }catch(IOException e){
            System.out.println("Failed reading file: " + file.path());
            e.printStackTrace();
        }
    }
}
