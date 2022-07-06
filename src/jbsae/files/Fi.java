package jbsae.files;

import java.io.*;

/** @author mzechner, Nathan Sweet */
public class Fi{
    public File file;

    public Fi(String name){
        this(new File(name));
    }

    public Fi(File file){
        this.file = file;
    }

    public Fi get(String name){
        File[] contents = file.listFiles();
        for(File f : contents) if(f.getName().equals(name)) return new Fi(f);
        return null;
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

    public FileInputStream input(){
        try{
            return new FileInputStream(path());
        }catch(Exception e){
            System.out.println("Failed reading file: " + path());
            e.printStackTrace();
            return null;
        }
    }

    public FileOutputStream output(){
        try{
            return new FileOutputStream(path());
        }catch(Exception e){
            System.out.println("Failed reading file: " + path());
            e.printStackTrace();
            return null;
        }
    }

    public BufferedReader reader(){
        return new BufferedReader(new InputStreamReader(input()));
    }

    public void create(){
        try{
            file.createNewFile();
        }catch(Exception e){
            System.out.println("Failed reading file: " + path());
            e.printStackTrace();
        }
    }
}
