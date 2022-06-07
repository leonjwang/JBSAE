package jbsae.graphics;

import jbsae.files.*;
import jbsae.graphics.gl.*;
import jbsae.struct.*;
import jbsae.struct.prim.*;

import java.io.*;
import java.util.*;

public class Font extends Asset{
    public static Seq<Font> all = new Seq<>();

    public String name;

    public Glyph none;
    public IntMap<Glyph> glyphs = new IntMap<>();
    public ObjiMap<String> data = new ObjiMap<>(); //TODO: Switch to fields
    public IntMap<Texture> pages = new IntMap<>();

    public String sizeKey = "size";
    public String pad1Key = "padding1", pad2Key = "padding2", pad3Key = "padding3", pad4Key = "padding4";

    public Font(String name){
        super(name);
        all.add(this);
    }

    @Override
    public Font create(){
        super.create();
        try{
            BufferedReader reader = reader();
            StringTokenizer info = new StringTokenizer(reader.readLine());
            if(!info.nextToken().equals("info")) throw new Exception("No font info");
            while(info.hasMoreTokens()){
                String[] split = info.nextToken().split("=");
                if(split[0].equals("face")) name = split[1].substring(1, split[1].length() - 1);
                else{
                    if(split[1].charAt(0) == '"') data.add(split[0], -1);
                    else if(split[1].contains(",")){
                        String[] parts = split[1].split(",");
                        for(int i = 0;i < parts.length;i++) data.add(split[0] + i, Integer.parseInt(parts[i]));
                    }else data.add(split[0], Integer.parseInt(split[1]));
                }
            }

            StringTokenizer common = new StringTokenizer(reader.readLine());
            if(!common.nextToken().equals("common")) throw new Exception("Missing common data");
            while(common.hasMoreTokens()){
                String[] split = common.nextToken().split("=");
                data.add(split[0], Integer.parseInt(split[1]));
            }

            for(int i = 0;i < data.get("pages");i++){
                StringTokenizer page = new StringTokenizer(reader.readLine());
                if(!page.nextToken().equals("page")) throw new Exception("Missing page data");

                int id = 0;
                String name = "";
                while(page.hasMoreTokens()){
                    String[] split = page.nextToken().split("=");
                    if(split[0].equals("id")) id = Integer.parseInt(split[1]);
                    else if(split[0].equals("file")) name = split[1].substring(1, split[1].length() - 1);
                }

                pages.add(id, (Texture)new Texture(parent() + "/" + name).load());
            }

            StringTokenizer chars = new StringTokenizer(reader.readLine());
            if(!chars.nextToken().equals("chars")) throw new Exception("Missing char number");
            int n = Integer.parseInt(chars.nextToken().split("=")[1]);
            for(int i = 0;i < n;i++){
                StringTokenizer charData = new StringTokenizer(reader.readLine());
                if(!charData.nextToken().equals("char")) throw new Exception("Missing char data");

                Glyph glyph = new Glyph(this);
                while(charData.hasMoreTokens()){
                    String[] split = charData.nextToken().split("=");
                    if(split[0].equals("id")) glyph.id = Integer.parseInt(split[1]);
                    else if(split[0].equals("x")) glyph.x = Integer.parseInt(split[1]);
                    else if(split[0].equals("y")) glyph.y = Integer.parseInt(split[1]);
                    else if(split[0].equals("width")) glyph.width = Integer.parseInt(split[1]);
                    else if(split[0].equals("height")) glyph.height = Integer.parseInt(split[1]);
                    else if(split[0].equals("xoffset")) glyph.xOffset = Integer.parseInt(split[1]);
                    else if(split[0].equals("yoffset")) glyph.yOffset = Integer.parseInt(split[1]);
                    else if(split[0].equals("xadvance")) glyph.xAdvance = Integer.parseInt(split[1]);
                    else if(split[0].equals("page")) glyph.page = Integer.parseInt(split[1]);
                    else if(split[0].equals("chnl")) glyph.channel = Integer.parseInt(split[1]);
                }
                glyphs.add(glyph.id, glyph);
                glyph.load();
            }

            none = new Glyph(this);
            none.x = 0;
            none.y = 0;
            none.width = 0;
            none.height = 0;
        }catch(Exception e){
            System.out.println("Failed loading font: " + path());
            e.printStackTrace();
        }
        return this;
    }

    public int size(){
        return -data.get(sizeKey);
    }
}
