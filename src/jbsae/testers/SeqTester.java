package jbsae.testers;

import jbsae.struct.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class SeqTester{
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
        new Test("Seq Value Test", () -> {
            String[] arr = generate();
            Seq<String> strings = new Seq<>(arr);
            for(int i = 0;i < arr.length;i++) if(!eql(strings.get(i), arr[i])) return false;
            return true;
        }).run();
        new Test("Seq Add Test", () -> {
            String[] arr = generate();
            Seq<String> strings = new Seq<>();
            for(int i = 0;i < arr.length;i++) strings.add(arr[i]);
            for(int i = 0;i < arr.length;i++) if(!eql(strings.get(i), arr[i])) return false;
            return true;
        }).run();
        new Test("Seq Insert Test", () -> {
            String[] arr = generate();
            Seq<String> strings = new Seq<>(arr);
            int insertIndex = randInt(0, strings.size - 1);
            String insertValue = generate()[0];
            strings.add(insertValue, insertIndex);
            for(int i = 0;i < arr.length;i++) if(!eql(arr[i], strings.get(i < insertIndex ? i : i + 1))) return false;
            if(!eql(strings.get(insertIndex), insertValue)) return false;
            return true;
        }).run();
        new Test("Seq Insert Flip Test", () -> {
            String[] arr = generate();
            Seq<String> strings = new Seq<>();
            for(int i = 0;i < arr.length;i++) strings.add(arr[i], 0);
            for(int i = 0;i < arr.length;i++) if(!eql(strings.get(i), arr[arr.length - i - 1])) return false;
            return true;
        }).run();
        new Test("Seq Size Test", () -> {
            String[] arr = generate();
            Seq<String> strings = new Seq<>(arr);
            if(arr.length != strings.size) return false;
            return true;
        }).run();
        new Test("Seq Iteration Test", () -> {
            String[] arr = generate();
            Seq<String> strings = new Seq<>(arr);
            int i = 0;
            for(String str : strings) if(!eql(str, arr[i++])) return false;
            if(i != arr.length) return false;
            return true;
        }).run();
        new Test("Seq Index Removal Test", () -> {
            Seq<String> strings = new Seq<>(generate());
            int removeIndex = randInt(0, strings.size - 1);
            Object[] values = strings.list();
            strings.remove(removeIndex);
            for(int i = 0;i < strings.size;i++) if(!eql(strings.get(i), values[i < removeIndex ? i : i + 1])) return false;
            return true;
        }).run();
        new Test("Seq Value Removal Test", () -> {
            Seq<String> strings = new Seq<>(generate());
            String removeValue = strings.get(randInt(0, strings.size - 1));
            strings.removeAll(removeValue);
            for(int i = 0;i < strings.size;i++) if(eql(strings.get(i), removeValue)) return false;
            return true;
        }).run();
        new Test("Seq Value Contains Test", () -> {
            Seq<String> strings = new Seq<>(generate());
            String value = strings.get(randInt(0, strings.size - 1));
            if(!strings.contains(value)) return false;
            return true;
        }).run();
    }
}
