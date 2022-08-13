package testers;

import jbsae.files.assets.*;
import jbsae.struct.*;
import jbsae.struct.prim.*;

import java.io.*;

import static jbsae.JBSAE.*;

public class AssetFinder{
    public static void main(String[] args){
        AssetDir root = new AssetDir("assets");
        Seq<AssetFi> assets = root.list(new Seq<AssetFi>());
        for(AssetFi asset : assets){
            CharSeq str = new CharSeq();
            str.add("AssetFi " + asset.path().replaceAll("\\.", "_").replaceAll(" ", "_").replaceAll("/", "_")).add(" = AssetFi.create(\"").add(asset.path()).add("\");");
            System.out.println(str);
        }
    }
}
