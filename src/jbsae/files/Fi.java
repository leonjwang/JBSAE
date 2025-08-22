package jbsae.files;

import java.io.*;

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

    public boolean directory(){
        return file.isDirectory();
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
            System.out.println("Failed reading file: " + path());
            e.printStackTrace();
            return null;
        }
    }

    public OutputStream output(){
        try{
            return new FileOutputStream(path());
        }catch(IOException e){
            System.out.println("Failed reading file: " + path());
            e.printStackTrace();
            return null;
        }
    }

    public BufferedReader reader(){
        return new BufferedReader(new InputStreamReader(input()));
    }


    public Fi create(){
        try{
            file.createNewFile();
        }catch(IOException e){
            System.out.println("Failed creating file: " + path());
            e.printStackTrace();
        }
        return this;
    }
}
