package jbsae.files;

import java.io.*;

public class Write{
    public Fi file;
    public FileOutputStream stream;

    public Write(Fi file){
        this.file = file;
        this.stream = file.output();
    }

    public void b(byte b){
        try{
            stream.write(b);
        }catch(Exception e){
            System.out.println("Failed reading file: " + file.path());
            e.printStackTrace();
        }
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
