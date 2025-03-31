package testers;

import jbsae.files.assets.*;
import jbsae.struct.*;
import jbsae.struct.prim.*;

import java.io.*;

import static jbsae.JBSAE.*;

public class AssetFinder{
    public static String[] banned = {" ", "-", ".", "\"", "'", "\\", "/", "(", ")", "[", "]", "{", "}"};

    public static void main(String[] args){
        AssetDir root = new AssetDir("assets");
        Seq<AssetFi> assets = root.list(new Seq<AssetFi>());
        for(AssetFi asset : assets){
            CharSeq str = new CharSeq();
            String path = asset.path();
            for(String s : banned) path = path.replace(s, "_");
            str.add("AssetFi " + path).add(" = AssetFi.create(\"").add(asset.path()).add("\");");
            System.out.println(str);
        }
    }
}
