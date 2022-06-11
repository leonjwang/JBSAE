package testers;

import jbsae.*;
import jbsae.core.*;
import jbsae.files.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Drawf.*;

public class DemoTester extends Screen{
    public static Block[][] world = new Block[20][20];

    public static TextureFile box;
    public static Block wall = new Block();

    public static Block get(int x, int y){
        if(x >= 0 && x < world.length && y >= 0 && y < world[0].length) return world[x][y];
        return wall;
    }

    @Override
    public void init(){
        box = new TextureFile("assets/sprites/effects/square.png").load();

        world[5][5] = new Liquid();
    }

    @Override
    public void update(){
        Block[][] tmp = new Block[world.length][world[0].length];
        for(int x = 0;x < world.length;x++){
            for(int y = 0;y < world[0].length;y++){
                tmp[x][y] = world[x][y];
            }
        }

        for(int x = 0;x < tmp.length;x++){
            for(int y = 0;y < tmp[0].length;y++){
                if(tmp[x][y] != null){
                    tmp[x][y].x = x;
                    tmp[x][y].y = y;
                    tmp[x][y].update();
                }else if(Tmp.r1.set(x * width / world.length, y * height / world[0].length, width / world.length, height / world[0].length).contains(input.mouse)){
                    world[x][y] = new Liquid();
                }
            }
        }
    }

    @Override
    public void draw(){
        push();
        box.texture.bind();
        for(int x = 0;x < world.length;x++){
            for(int y = 0;y < world[0].length;y++){
                if(world[x][y] != null){
                    translatet(x * width / world.length, y * height / world[0].length);
                    world[x][y].draw();
                }
            }
        }
        pop();
    }

    public static void main(String[] args){
        JBSAE.debug = false;

        JBSAE.init();
        JBSAE.screen(new DemoTester());
        JBSAE.start();
        JBSAE.dispose();
    }

    public static class Block{
        public int x, y;

        public void update(){
        }

        public void draw(){
        }
    }

    public static class Liquid extends Block{
        public Pool pool;
        public int update = 3;

        public Liquid(){
            pool = new Pool();
        }

        public Liquid(Pool pool){
            pool.amount++;
            this.pool = pool;
        }

        public float amount(){
            return pool.total / pool.amount;
        }

        @Override
        public void draw(){
            fill(1f, 1f, 1f);
            rect(0, 0, width / world.length, amount() * height / world[0].length);
        }

        @Override
        public void update(){
            if(time.updates % update != 0) return;
            if(pool.amount == 0 || pool.total < 1f){
                world[x][y] = null;
                return;
            }
            if(get(x, y - 1) == null){
                world[x][y - 1] = new Liquid();
                pool.total--;
            }else if(get(x, y - 1) instanceof Liquid && (get(x, y - 1) instanceof Liquid && ((Liquid)get(x, y - 1)).amount() < 1)){
                Liquid l = (Liquid)get(x, y - 1);
                float tmp = l.amount();
                l.pool.total -= tmp;
                l.pool.amount --;
                Liquid n = new Liquid();
                n.pool.total = tmp;

                float max = Math.max(1f - n.amount(), amount());
                pool.total -= Math.min(max, 1f);
                n.pool.total += Math.min(max, 1f);

                world[x][y - 1] = n;
            }else{
                if(get(x - 1, y) == null) world[x - 1][y] = new Liquid(pool);
                else if(get(x - 1, y) instanceof Liquid){
                    Liquid l = (Liquid)get(x - 1, y);
                    if(l.pool != pool){
                        pool.add(l.pool);
                        l.pool = pool;
                    }
                }
                if(get(x + 1, y) == null) world[x + 1][y] = new Liquid(pool);
            }
        }

        public class Pool{
            public float total = 1f;
            public int amount = 1;

            public void add(Pool p){
                total += p.total;
                amount += p.amount;
            }
        }
    }
}
