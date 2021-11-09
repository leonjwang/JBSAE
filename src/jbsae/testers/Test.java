package jbsae.testers;

import static jbsae.util.Stringf.*;

public class Test{
    public String name;
    public Runnable test;

    public Test(String name, Runnable test){
        this.name = name;
        this.test = test;
    }

    public void run(){
        print("Running test: [" + name + "]");
        long start = System.currentTimeMillis();
        test.run();
        long end = System.currentTimeMillis();
        print("Runtime: " + (start - end) + "ms + (" + ((start - end) / 1000f) + "s)");
    }
}
