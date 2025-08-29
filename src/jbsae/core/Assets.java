package jbsae.core;

import jbsae.audio.*;
import jbsae.files.*;
import jbsae.files.assets.*;
import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.struct.*;

import java.io.*;

// TODO: Sound.all, Texture.all, Shader.all are somewhat redundent, but Font.all and Source.all are the odd ones out.
public class Assets{
    public static Map<String, AssetFi> files = new Map<>();

    // TODO: Replace with one map?
    public Map<String, Font> fonts = new Map<>();
    public Map<String, Shader> shaders = new Map<>();
    public Map<String, Sound> sounds = new Map<>();
    public Map<String, Texture> textures = new Map<>();

    public String assetsFolder = "assets";
    public String assetList = "assetlist.lst";

    public boolean jar = true;

    public AssetDir root;

    public Assets(){
    }

    public void init(){
        root = new AssetDir(assetsFolder);

        if(root.file.exists() && root.file.isDirectory()) jar = false;

        if(!jar) gen();
        else{
            String assetListFile = assetsFolder + "/" + assetList;
            AssetFi file = new AssetFi(assetListFile);
            try(BufferedReader reader = file.reader()){
                String line;
                while((line = reader.readLine()) != null && line.length() > 0) create(line);
            }catch(IOException e){
                System.out.println("Failed read asset list file: " + assetListFile);
                e.printStackTrace();
            }
        }
    }

    // TODO: This is scuffed; It dynamically loads the assets and also generated the assets list.
    private void gen(){
        String assetListFile = assetsFolder + "/" + assetList;
        Fi file = new Fi(assetListFile);
        file.create();
        try(BufferedWriter writer = file.writer()){
            Seq<AssetFi> assets = root.list(new Seq<AssetFi>());
            for(AssetFi asset : assets){
                create(asset.path());
                writer.append(asset.path()).append('\n');
            }
        }catch(IOException e){
            System.out.println("Failed to create asset list file: " + assetListFile);
            e.printStackTrace();
        }
    }

    public void load(){
        for(String assetName : files) files.get(assetName).load();
    }

    public void dispose(){
        for(Shader shader : Shader.all) shader.dispose();
        for(Sound sound : Sound.all) sound.dispose();
        for(Texture texture : Texture.all) texture.dispose();
    }

    public AssetFi create(String path){
        if(files.contains(path)) return files.get(path);
        if(path.endsWith(".fnt")) return new FontFi(path);
        if(path.endsWith(".frag")) return new ShaderFi(path);
        if(path.endsWith(".vert")) return new ShaderFi(path);
        if(path.endsWith(".au")) return new SoundFi(path);
        if(path.endsWith(".mp3")) return new SoundFi(path);
        if(path.endsWith(".ogg")) return new SoundFi(path);
        if(path.endsWith(".wav")) return new SoundFi(path);
        if(path.endsWith(".png")) return new TextureFi(path);
        return null;
    }
}
