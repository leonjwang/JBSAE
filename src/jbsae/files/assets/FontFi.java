package jbsae.files.assets;

import jbsae.*;
import jbsae.graphics.*;
import jbsae.graphics.gl.*;

import java.io.*;
import java.util.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Stringf.*;

public class FontFi extends AssetFi{
    public Font font;

    public FontFi(String path){
        super(path);
    }

    @Override
    public FontFi gen(){
        Log.info("Loading font: " + path());
        try(BufferedReader reader = reader()){
            font = new Font();
            StringTokenizer info = new StringTokenizer(reader.readLine());
            if(!info.nextToken().equals("info")) throw new IOException("No font info");

            while(info.hasMoreTokens()){
                String[] split = info.nextToken().split("=");

                if(split[0].equals("face")) font.name = split[1].substring(1, split[1].length() - 1);
                else if(split[0].equals("size")) font.size = -Integer.parseInt(split[1]);
                else if(split[0].equals("unicode")) font.unicode = Integer.parseInt(split[1]);
                else if(split[0].equals("bold")) font.bold = Integer.parseInt(split[1]) == 1;
                else if(split[0].equals("italic")) font.italic = Integer.parseInt(split[1]) == 1;
                else if(split[0].equals("stretchH")) font.stretchH = Integer.parseInt(split[1]);
                else if(split[0].equals("smooth")) font.smooth = Integer.parseInt(split[1]);
                else if(split[0].equals("aa")) font.aa = Integer.parseInt(split[1]);
                else if(split[0].equals("outline")) font.outline = Integer.parseInt(split[1]);
                else if(split[0].equals("charset")) font.charset = split[1].substring(1, split[1].length() - 1);
                else if(split[0].equals("padding")){
                    String[] padding = split[1].split(",");
                    font.padLeft = Integer.parseInt(padding[0]);
                    font.padTop = Integer.parseInt(padding[1]);
                    font.padRight = Integer.parseInt(padding[2]);
                    font.padBot = Integer.parseInt(padding[3]);
                }else if(split[0].equals("spacing")){
                    String[] spacing = split[1].split(",");
                    font.spacing = Integer.parseInt(spacing[0]);
                }
            }

            // Parse common properties
            StringTokenizer common = new StringTokenizer(reader.readLine());
            if(!common.nextToken().equals("common")) throw new IOException("Missing common data");
            while(common.hasMoreTokens()){
                String[] split = common.nextToken().split("=");
                if(split[0].equals("pages")) font.pages = Integer.parseInt(split[1]);
            }

            // Load texture pages
            font.page = new Texture[font.pages];
            for(int i = 0;i < font.pages;i++){
                StringTokenizer page = new StringTokenizer(reader.readLine());
                if(!page.nextToken().equals("page")) throw new IOException("Missing page data");

                int id = 0;
                String name = "";

                while(page.hasMoreTokens()){
                    String[] split = page.nextToken().split("=");
                    if(split[0].equals("id")) id = Integer.parseInt(split[1]);
                    else if(split[0].equals("file")) name = split[1].substring(1, split[1].length() - 1);
                }

                font.page[id] = ((TextureFi)assets.create(parent() + "/" + name).load()).texture;
            }

            // Parse glyphs
            StringTokenizer chars = new StringTokenizer(reader.readLine());
            if(!chars.nextToken().equals("chars")) throw new IOException("Missing char number");
            int n = Integer.parseInt(chars.nextToken().split("=")[1]);
            for(int i = 0;i < n;i++){
                StringTokenizer charData = new StringTokenizer(reader.readLine());
                if(!charData.nextToken().equals("char")) throw new IOException("Missing char data");

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

            assets.fonts.add(name, font);
        }catch(Exception e){
            Log.error("Failed loading font: " + path());
            Log.error(getStackTrace(e));
        }
        return (FontFi)super.gen();
    }
}
