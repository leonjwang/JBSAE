package jbsae.testers;

import jbsae.func.prim.*;

import static jbsae.util.Stringf.*;

public class Test{
    public String name;
    public Boolp test;
    public int times;

    public Test(String name, Boolp test){
        this.name = name;
        this.test = test;
        this.times = 1000;
    }

    public void run(){
        print("Running test: [" + name + "]");
        long start = System.currentTimeMillis();
        boolean result = true;
        for(int i = 0;i < times;i++) result = result & test.get();
        print("Result: " + result);
        long end = System.currentTimeMillis();
        print("Runtime: " + (end - start) + "ms + (" + ((end - start) / 1000f) + "s)");
        print("");
    }
}
