package jbsae.testers;

import jbsae.struct.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class SetTester{
    public static String[] generate(){
        int size = randInt(10, 100);
        String[] arr = new String[size];
        for(int i = 0;i < size;i++){
            String value = "";
            for(int j = 0;j < randInt(1, 10);j++) value += (char)randInt(32, 126);
            arr[i] = value;
        }
        return arr;
    }

    public static void main(String[] args){
        new Test("Set Value Test", () -> {
            String[] arr = generate();
            Set<String> strings = new Set<>(arr);
            if(strings.size > arr.length) return false;
            return true;
        }).run();
        new Test("Set Add Test", () -> {
            String[] arr = generate();
            Set<String> strings = new Set<>();
            for(int i = 0;i < arr.length;i ++)  strings.add(arr[i]);
            if(strings.size > arr.length) return false;
            return true;
        }).run();
        new Test("Set Value Contains Test", () -> {
            String[] arr = generate();
            Seq<String> base = new Seq<>(arr);
            Set<String> strings = new Set<>(arr);
            for(String str : strings){
                if(!base.contains(str)) return false;
                base.removeAll(str);
            }
            if(base.size > 0) return false;
            return true;
        }).run();
        new Test("Set Contains Test", () -> {
            String[] arr = generate();
            Seq<String> base = new Seq<>(arr);
            Set<String> strings = new Set<>(arr);
            String get = base.get(randInt(0, arr.length - 1));
            if(!strings.contains(get)) return false;
            return true;
        }).run();
        new Test("Set Duplication Test", () -> {
            String[] arr = generate();
            Set<String> base = new Set<>();
            for(int i = 0;i < randInt(2, 10);i ++) base.add(arr[0]);
            if(base.size > 1) return false;
            return true;
        }).run();
        new Test("Set Remove Test", () -> {
            String[] arr = generate();
            Set<String> strings = new Set<>(arr);
            int originalSize = strings.size;
            strings.remove(arr[randInt(0, arr.length - 1)]);
            if(strings.size != originalSize - 1) return false;
            return true;
        }).run();
    }
}
