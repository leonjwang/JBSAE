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

    public byte[] bytes(InputStream input){
        try{
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int read;
            byte[] data = new byte[4];

            while((read = input.read(data, 0, data.length)) != -1) buffer.write(data, 0, read);

            buffer.flush();
            return buffer.toByteArray();
        }catch(IOException e){
            System.out.println("Failed reading file: " + path());
            e.printStackTrace();
            return null;
        }
    }


    public Fi create(){
        try{
            file.createNewFile();
        }catch(IOException e){
            System.out.println("Failed reading file: " + path());
            e.printStackTrace();
        }
        return this;
    }
}
