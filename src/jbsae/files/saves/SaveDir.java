package jbsae.files.saves;

import jbsae.files.*;
import jbsae.struct.*;

import java.io.*;

public class SaveDir extends Fi{
    public SaveDir(String name){
        super(name);
    }

    public SaveDir(File file){
        super(file);
    }

    public Seq<SaveFi> list(Seq<SaveFi> arr){
        File[] contents = file.listFiles();
        for(File file : contents){
            if(file.isDirectory()) new SaveDir(file).list(arr);
            else arr.add(new SaveFi(file.getPath()));
        }
        return arr;
    }
}
