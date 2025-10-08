package jbsae.core;

import jbsae.*;
import jbsae.audio.*;
import jbsae.files.*;
import jbsae.files.assets.*;
import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.struct.*;

import java.io.*;

import static jbsae.util.Stringf.*;

// TODO: static .all Seqs are somewhat scuffed
public class Assets{
    public static Map<String, AssetFi> files = new Map<>();

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
            Log.info("Loading assets from list...");
            String assetListFile = assetsFolder + "/" + assetList;
            AssetFi file = new AssetFi(assetListFile);
            try(BufferedReader reader = file.reader()){
                String line;
                while((line = reader.readLine()) != null && line.length() > 0) create(line);
            }catch(IOException e){
                Log.error("Failed read asset list file: " + assetListFile);
                Log.error(getStackTrace(e));
            }
        }
    }

    /** Generate and load assets/list file from assets folder. */
    private void gen(){
        Log.info("Generating asset list...");
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
            Log.error("Failed to create asset list file: " + assetListFile);
            Log.error(getStackTrace(e));
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
        Log.info("Creating asset: " + path);
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
