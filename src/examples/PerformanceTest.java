package examples;

import jbsae.*;
import jbsae.Log.*;
import jbsae.core.*;
import jbsae.core.loop.*;
import jbsae.math.*;
import jbsae.struct.*;
import jbsae.struct.tree.*;
import jbsae.util.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Mathf.*;

// Baseline: 9 fps
public class PerformanceTest{
    public static void main(String[] args){
        jbsae(() -> {
            loop = new DynamicLoop(10000);

            screen(new Screen(){
                public void update(){
                    QuadTree tree = new QuadTree(10000, 10000).valueLimit(32);
                    for(int i = 0;i < 10000;i++){
                        Vec2 v = new Vec2(random(0, 10000), random(0, 10000));
                        tree.add(v);
                    }

                    for(int i = 0;i < 10000;i++){
                        Range2 range = new Range2(random(0, 8000), random(0, 8000), random(0, 2000), random(0, 2000));
                        Seq<Pos2> inside = new Seq<>();
                        tree.query(inside, range);
                        Set<Pos2> insideSet = new Set<>();
                        for(Pos2 p : inside) insideSet.add(p);
                        inside.clear();
                    }
                }
            });
        });
    }
}
