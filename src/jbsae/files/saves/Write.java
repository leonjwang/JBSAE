package jbsae.files.saves;

import jbsae.*;
import jbsae.files.*;

import java.io.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;

public class Write{
    public Fi file;
    public OutputStream stream;

    public Write(Fi file){
        this.file = file;
        this.stream = file.output();
    }

    public Write b(byte b){
        try{
            stream.write(b);
        }catch(IOException e){
            Log.error("Failed reading byte from file: " + file.path());
            Log.error(getStackTrace(e));
        }
        return this;
    }

    public Write s(short s){
        return b((byte)(s >> 8)).b((byte)s);
    }

    public Write c(char c){
        return s((short)c);
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

    public Write str(String str){
        byte[] bytes = str.getBytes();
        i(bytes.length);
        try{
            stream.write(bytes);
        }catch(IOException e){
            Log.error("Failed writing string to file: " + file.path());
            Log.error(getStackTrace(e));
        }
        return this;
    }

    public void close(){
        try{
            stream.close();
        }catch(IOException e){
            Log.error("Failed closing output stream to file: " + file.path());
            Log.error(getStackTrace(e));
        }
    }
}
