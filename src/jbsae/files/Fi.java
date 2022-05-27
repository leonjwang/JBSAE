package jbsae.files;

import java.io.*;

/** @author mzechner, Nathan Sweet */
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

    public BufferedReader reader(){
        try{
            return new BufferedReader(new InputStreamReader(new FileInputStream(path())));
        }catch(Exception e){
            System.out.println("Failed reading file: " + path());
            e.printStackTrace();
            return null;
        }
    }
}
