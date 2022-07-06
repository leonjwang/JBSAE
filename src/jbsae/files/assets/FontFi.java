package jbsae.files.assets;

import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.struct.Set;

import java.io.*;
import java.util.*;

public class FontFi extends AssetFi{
    public Font font;

    public FontFi(String name){
        super(name);
    }

    public FontFi(File file){
        super(file);
    }

    @Override
    public FontFi load(){
        return (FontFi)super.load();
    }

    @Override
    public FontFi create(){
        super.create();
        try{
            font = new Font();
            BufferedReader reader = reader();
            StringTokenizer info = new StringTokenizer(reader.readLine());
            if(!info.nextToken().equals("info")) throw new Exception("No font info");
            while(info.hasMoreTokens()){
                String[] split = info.nextToken().split("=");
                if(split[0].equals("face")) font.name = split[1].substring(1, split[1].length() - 1);
                else{
                    if(split[0].equals("size")) font.size = -Integer.parseInt(split[1]);
                    else if(split[0].equals("unicode")) font.unicode = Integer.parseInt(split[1]);
                    //TODO: Finish porting
                }
            }

            StringTokenizer common = new StringTokenizer(reader.readLine());
            if(!common.nextToken().equals("common")) throw new Exception("Missing common data");
            while(common.hasMoreTokens()){
                String[] split = common.nextToken().split("=");
                if(split[0].equals("pages")) font.pages = Integer.parseInt(split[1]);
            }

            font.page = new Texture[font.pages];
            for(int i = 0;i < font.pages;i++){
                StringTokenizer page = new StringTokenizer(reader.readLine());
                if(!page.nextToken().equals("page")) throw new Exception("Missing page data");

                int id = 0;
                String name = "";
                while(page.hasMoreTokens()){
                    String[] split = page.nextToken().split("=");
                    if(split[0].equals("id")) id = Integer.parseInt(split[1]);
                    else if(split[0].equals("file")) name = split[1].substring(1, split[1].length() - 1);
                }

                font.page[id] = new TextureFi(parent() + "/" + name).load().texture;
            }

            StringTokenizer chars = new StringTokenizer(reader.readLine());
            if(!chars.nextToken().equals("chars")) throw new Exception("Missing char number");
            int n = Integer.parseInt(chars.nextToken().split("=")[1]);
            for(int i = 0;i < n;i++){
                StringTokenizer charData = new StringTokenizer(reader.readLine());
                if(!charData.nextToken().equals("char")) throw new Exception("Missing char data");

                Glyph glyph = new Glyph(font);
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
                font.glyphs.add(glyph.id, glyph);
                glyph.load();
            }

            font.none = new Glyph(font);
        }catch(Exception e){
            System.out.println("Failed loading font: " + path());
            e.printStackTrace();
        }
        return this;
    }
}
