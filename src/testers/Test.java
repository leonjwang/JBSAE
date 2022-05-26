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
        System.out.println("\nRunning test: [" + name + "]");
        long start = System.currentTimeMillis();
        boolean result = true;
        for(int i = 0;i < times;i++) result = result & test.get();
        System.out.println("[" + name + "] Result: " + result);
        long end = System.currentTimeMillis();
        System.out.println("[" + name + "] Runtime: " + (end - start) + "ms " + "(" + ((end - start) / 1000f) + "s)");

        if(!result) new Exception("Test: [" + name + "] has failed!").printStackTrace();
        return result;
    }
}
