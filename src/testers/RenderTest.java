package testers;

import jbsae.*;
import jbsae.core.*;
import jbsae.files.assets.*;
import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.math.*;
import jbsae.util.*;

import static jbsae.JBSAE.*;

public class RenderTest extends Screen{
    public Pixel[][] arr;

    public Vec2 translate = new Vec2(200, 200);
    public float scale = 5;

    public float dist = 2;

    public int size = 20;

    public class Pixel{
        public Vec2 pos = new Vec2();
        public Vec2 vel = new Vec2();
        public Color color;

        public Pixel(int x, int y){
            pos.set(x, y);
            color = new Color().hsv(pos.x * 10 + pos.y * 10, 1f, 1f);
        }

        public void update(){
            pos.add(vel);

            Vec2 real = new Vec2(input.mouse).sub(translate).scl(1 / scale);
//            if(Tmp.e1.set(pos.x, pos.y, dist).contains(real)){
//                vel.add(Tmp.v1.set(pos).sub(real).nor().scl(0.25f));
//            }

            if(input.clicking[0]){
                vel.add(Tmp.v1.set(real).sub(pos).nor().scl(0.1f));
            }

//            if(!Tmp.r1.set(-size, -size, size * 2, size * 2).contains(pos)){
//                Tmp.r1.set(-size, -size, size * 2, size * 2).constrain(pos);
////                vel.inv();
//            }
            if(!Tmp.e1.set(0, 0, size * 2).contains(pos)){
                Tmp.e1.set(0, 0, size * 2).constrain(pos);
                vel.inv();
            }

            vel.scl(0.99f);
        }

        public void draw(){
//            Drawf.layer(Mathf.random());
            Drawf.fill(Tmp.c1.set(color).a(0.2f));
            for(int i = 0;i < 10;i++) Drawf.rectc(pos.x * scale + translate.x, pos.y * scale + translate.y, scale * i / 5f, scale * i / 5f);
        }
    }

    @Override
    public void init(){
        arr = new Pixel[50][50];
        for(int x = 0;x < arr.length;x++){
            for(int y = 0;y < arr[x].length;y++) arr[x][y] = new Pixel(x, y);
        }

//        draw = new SortedDraw();
    }

    @Override
    public void update(){
        for(int x = 0;x < arr.length;x++){
            for(int y = 0;y < arr[x].length;y++) arr[x][y].update();
        }
    }

    @Override
    public void draw(){
        assets.textures.get("circle.png").bind();

        for(int x = 0;x < arr.length;x++){
            for(int y = 0;y < arr[x].length;y++) arr[x][y].draw();
        }
    }

    public static void main(String[] args){
        JBSAE.width = 400;

        JBSAE.init();
        JBSAE.load();
        JBSAE.screen(new RenderTest());
        JBSAE.start();
        JBSAE.dispose();
    }
}
