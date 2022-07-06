package jbsae.files;

import java.io.*;

public class Read{
    public Fi file;
    public FileInputStream stream;

    public Read(Fi file){
        this.file = file;
        this.stream = file.input();
    }

    public byte b(){
        try{
            return (byte)stream.read();
        }catch(Exception e){
            System.out.println("Failed reading file: " + file.path());
            e.printStackTrace();
            return -1;
        }
    }

    public short s(){
        return (short)(b() << 8 + b());
    }

    public void close(){
        try{
            stream.close();
        }catch(Exception e){
            System.out.println("Failed reading file: " + file.path());
            e.printStackTrace();
        }
    }
}
