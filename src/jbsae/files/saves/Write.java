package jbsae.files.saves;

import jbsae.files.*;

import java.io.*;

import static jbsae.util.Mathf.*;

public class Write{
    public Fi file;
    public OutputStream stream;

    public Write(Fi file){
        this.file = file;
        this.stream = file.output();
    }

    public Write b(byte b){
        try{
            stream.write((byte)b);
        }catch(Exception e){
            System.out.println("Failed reading file: " + file.path());
            e.printStackTrace();
        }
        return this;
    }

    public Write s(short s){
        return b((byte)(s >> 8)).b((byte)s);
    }

    public Write i(int i){
        return s((short)(i >> 16)).s((short)i);
    }

    public Write l(long l){
        return i((int)(l >> 32)).i((int)l);
    }

    public Write f(float f){
        return i(intBits(f));
    }

    public Write d(double d){
        return l(longBits(d));
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
