package jbsae.testers;

import jbsae.struct.*;

import static jbsae.util.Mathf.*;

public class SeqTester{
    public static void main(String[] args){

        int size = (int)randInt(0, 100);
        Seq<String> strings = new Seq<>();
        String[] stringArr = new String[size];
        for(int i = 0;i < size;i ++){
            String value = "";
            for(int j = 0;j < randInt(1, 10);j++) value += (char)randInt(32, 126);
            stringArr[i] = value;
            strings.add(value);
        }
        for(int i = 0;i < size;i ++){
            System.out.println(stringArr[i] + ":" + strings.get(i));
        }
    }
}
