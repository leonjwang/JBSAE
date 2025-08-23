package jbsae.core;

import jbsae.audio.*;
import jbsae.files.*;
import jbsae.files.assets.*;
import jbsae.graphics.gl.*;
import jbsae.struct.*;
import jbsae.struct.prim.*;

import java.io.*;

import static jbsae.JBSAE.*;

// TODO: Consider moving Sound.all, Texture.all, etc static fields to here
public class Assets{
    public String assetsFolder = "assets";
    public String assetList = "assetlist.lst";

    public boolean jar = true;

    public AssetDir root;

    public Assets(){
    }

    public void init(){
        this.root = new AssetDir(assetsFolder);

        if(root.file.exists() && root.file.isDirectory()) jar = false;

        if(!jar) genAssetList();

        String assetListFile = assetsFolder + "/" + assetList;
        AssetFi file = new AssetFi(assetListFile);
        try(BufferedReader reader = file.reader()){
            String line;
            while((line = reader.readLine()) != null && line.length() > 0) AssetFi.create(line);
        }catch(IOException e){
            System.out.println("Failed read asset list file: " + assetListFile);
            e.printStackTrace();
        }
    }

    private void genAssetList(){
        String assetListFile = assetsFolder + "/" + assetList;
        Fi file = new Fi(assetListFile);
        file.create();
        try(BufferedWriter writer = file.writer()){
            Seq<AssetFi> assets = root.list(new Seq<AssetFi>());
            for(AssetFi asset : assets) writer.append(asset.path()).append('\n');
        }catch(IOException e){
            System.out.println("Failed to create asset list file: " + assetListFile);
            e.printStackTrace();
        }
    }

    public void load(){
        for(String assetName : AssetFi.all) AssetFi.all.get(assetName).load();
    }

    public void dispose(){
        for(Shader shader : Shader.all) shader.dispose();
        for(Sound sound : Sound.all) sound.dispose();
        for(Source source : Source.all) source.dispose();
        for(Texture texture : Texture.all) texture.dispose();
    }

}
