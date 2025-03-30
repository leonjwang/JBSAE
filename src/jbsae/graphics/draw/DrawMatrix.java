package jbsae.graphics.draw;

import jbsae.graphics.*;
import jbsae.math.*;
import jbsae.struct.*;
import jbsae.util.*;

import static jbsae.util.Colorf.*;
import static jbsae.util.Mathf.*;

public class DrawMatrix{
    public Queue<Transformation> transformations = new Queue<>();


    public void rotate(float r){
        transformations.addFirst(new Rotation(r));
    }

    public void scale(float x, float y){
        transformations.addFirst(new Scale(x, y));
    }

    public void translate(float x, float y){
        transformations.addFirst(new Translation(x, y));
    }

    public class Transformation{
        public float x(float x){
            return x;
        }

        public float y(float y){
            return y;
        }
    }

    public class Rotation extends Transformation{
        public float r;


        public Rotation(float r){
            this.r = r;
        }

        public float x(float x){
            return x * cos(r);
        }

        public float y(float y){
            return y * cos(r);
        }
    }

    public class Scale extends Transformation{
        public float x, y;


        public Scale(float x, float y){
            this.x = x;
            this.y = y;
        }

        public float x(float x){
            return x * this.x;
        }

        public float y(float y){
            return y * this.y;
        }
    }

    public class Translation extends Transformation{
        public float x, y;


        public Translation(float x, float y){
            this.x = x;
            this.y = y;
        }

        public float x(float x){
            return x + this.x;
        }

        public float y(float y){
            return y + this.y;
        }
    }
}
