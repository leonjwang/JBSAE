package examples;

import jbsae.core.*;
import jbsae.core.loop.*;
import jbsae.struct.tree.*;
import jbsae.util.*;

import static jbsae.JBSAE.*;

//-XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining
// Baseline: 9 fps
public class CollisionTest{
//    public static void main(String[] args){
//        jbsae(() -> {
//            loop = new DynamicLoop(10000);
//
//            screen(new Screen(){
//                public void update(){
//                    QuadTree tree = new QuadTree(10000, 10000).valueLimit(32);
//                    for(int i = 0;i < 10000;i++){
//                        Vec2 v = new Vec2(random(0, 10000), random(0, 10000));
//                        tree.add(v);
//                    }
//
//                    for(int i = 0;i < 10000;i++){
//                        Range2 range = new Range2(random(0, 8000), random(0, 8000), random(0, 2000), random(0, 2000));
//                        Seq<Pos2> inside = new Seq<>();
//                        tree.query(inside, range);
//                        Set<Pos2> insideSet = new Set<Pos2>().ensure(inside.size);
//                        for(Pos2 p : inside) insideSet.add(p);
//                        inside.clear();
//                    }
//                }
//            });
//        });
//    }

    public static void main(String[] args){
        jbsae(() -> {
            loop = new DynamicLoop(10000);

            screen(new Screen(){
                public void update(){
                    Heap<Integer> heap = new Heap<>(i -> i);

                    for(int i = 0;i < 10000;i++) heap.push(Mathf.randInt(0, 10000));
                    for(int i = 0;i < 10000;i++) heap.pop();
                }
            });
        });
    }
}
