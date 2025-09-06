package jbsae.files;

import jbsae.*;

import java.io.*;

import static jbsae.util.Stringf.*;

public class Fi{
    public File file;

    public Fi(){
    }

    public Fi(String name){
        this(new File(name));
    }

    public Fi(File file){
        this.file = file;
    }

    public boolean exists(){
        return file.exists();
    }

    public String name(){
        return file.getName();
    }

    public String path(){
        return file.getPath();
    }

    public String parent(){
        return file.getParent();
    }

    public InputStream input(){
        try{
            return new FileInputStream(path());
        }catch(IOException e){
            Log.error("Failed creating input stream to file: " + path());
            Log.error(getStackTrace(e));
            return null;
        }
    }

    public OutputStream output(){
        try{
            return new FileOutputStream(path());
        }catch(IOException e){
            Log.error("Failed creating output stream to file: " + path());
            Log.error(getStackTrace(e));
            return null;
        }
    }

    public BufferedReader reader(){
        return new BufferedReader(new InputStreamReader(input()));
    }

    public BufferedWriter writer(){
        return new BufferedWriter(new OutputStreamWriter(output()));
    }


    public Fi create(){
        try{
            file.createNewFile();
        }catch(IOException e){
            Log.error("Failed creating file: " + path());
            Log.error(getStackTrace(e));
        }
        return this;
    }
}
