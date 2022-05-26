package jbsae.graphics;

import jbsae.struct.prim.*;

import java.io.*;
import java.util.*;

public class Font{
    public Glyph none;
    public IntMap<Glyph> glyphs;

    public Font(String path){
        load(path);
    }

    public void load(String path){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path + "/data.fnt")));
            StringTokenizer info = new StringTokenizer(reader.readLine());
            if(!info.nextToken().equals("info")) throw new Exception("No font info");
            while(info.hasMoreTokens()){

            }

        }catch(Exception e){
            System.out.println("Failed loading font: " + path);
            e.printStackTrace();
        }
    }
}
