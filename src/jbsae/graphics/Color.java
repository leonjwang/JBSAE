package jbsae.graphics;

public class Color{
    public float r, g, b, a;

    public Color(){
    }

    public Color(float r, float g, float b){
        this(r, g, b, 1f);
    }

    public Color(float r, float g, float b, float a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
}
