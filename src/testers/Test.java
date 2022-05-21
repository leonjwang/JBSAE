package testers;

import jbsae.func.prim.*;

public class Test{
    public String name;
    public Boolp test;
    public int times;

    public Test(String name, Boolp test){
        this(name, 1000, test);
    }

    public Test(String name, int times, Boolp test){
        this.name = name;
        this.test = test;
        this.times = times;
    }

    public boolean run(){
        print("Running test: [" + name + "]");
        long start = System.currentTimeMillis();
        boolean result = true;
        for(int i = 0;i < times;i++) result = result & test.get();
        print("[" + name + "] Result: " + result);
        long end = System.currentTimeMillis();
        print("[" + name + "] Runtime: " + (end - start) + "ms " + "(" + ((end - start) / 1000f) + "s)");
        return result;
    }
}
