package examples;

import jbsae.*;
import jbsae.core.*;
import jbsae.struct.*;
import jbsae.struct.prim.*;
import jbsae.util.*;

import static jbsae.JBSAE.*;

public class HanoiVisualization{
    public static Seq<Move> moves = new Seq<>();

    public static void hanoi(int n, Tower a, Tower b, Tower c){
        if(n == 0) return;

        hanoi(n - 1, a, c, b);

        int moved = a.queue.last();
        b.queue.addLast(moved);
        a.queue.removeLast();
        moves.add(new Move(a, b));

        hanoi(n - 1, c, b, a);
    }

    public static final int count = 10;
    public static Tower a = new Tower(), b = new Tower(), c = new Tower();

    public static void main(String[] args){
        for(int i = 0;i < count;i++) a.queue.addFirst(i + 1);

        hanoi(count, a, b, c);

        b.queue.clear();
        for(int i = 0;i < count;i++) a.queue.addFirst(i + 1);

        JBSAE.jbsae(() -> {
            JBSAE.screen(new Screen(){
                public int counter = 0;
                public int period = 5;
                public int move = 0;

                public void update(){
                    counter++;
                    if(counter > period){
                        counter = counter % period;

                        if(move >= moves.size) return;

                        Move current = moves.get(move++);

                        int moved = current.from.queue.last();
                        current.from.queue.removeLast();
                        current.to.queue.addLast(moved);
                    }
                }

                public void draw(){
                    assets.textures.get("square.png").bind();

                    Drawf.fill(Colorf.WHITE);

                    Drawf.rectc(100, 300, 10, 200);
                    for(int i = 0;i < a.queue.size;i++){
                        Drawf.rectc(100, 200 + i * 20, (a.queue.get(i) + 2) * 15, 10);
                    }

                    Drawf.rectc(300, 300, 10, 200);
                    for(int i = 0;i < b.queue.size;i++){
                        Drawf.rectc(300, 200 + i * 20, (b.queue.get(i) + 2) * 15, 10);
                    }

                    Drawf.rectc(500, 300, 10, 200);
                    for(int i = 0;i < c.queue.size;i++){
                        Drawf.rectc(500, 200 + i * 20, (c.queue.get(i) + 2) * 15, 10);
                    }

                }
            });
        });
    }

    public static class Tower{
        public IntQueue queue = new IntQueue();

        public Tower(){
        }
    }

    public static class Move{
        public Tower from, to;

        public Move(Tower from, Tower to){
            this.from = from;
            this.to = to;
        }
    }
}
