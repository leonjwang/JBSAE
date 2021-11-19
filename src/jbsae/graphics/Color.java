package jbsae.graphics;

import static jbsae.util.Mathf.*;

/** @author mzechner */
public class Color{
    public float r, g, b, a;

    public Color(){
    }

    public Color(int rgba8888){
        rgba8888(rgba8888);
    }

    public Color(Color c){
        this(c.r, c.g, c.b, c.a);
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

    public Color r(float r){
        this.r = r;
        return this;
    }

    public Color g(float g){
        this.g = g;
        return this;
    }

    public Color b(float b){
        this.b = b;
        return this;
    }

    public Color a(float a){
        this.a = a;
        return this;
    }

    public Color set(float r, float g, float b){
        return set(r, g, b, a);
    }

    public Color set(float r, float g, float b, float a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        return this;
    }

    public Color hsv(float h, float s, float v){
        float x = mod(h / 60f + 6, 6);
        int i = (int)x;
        float f = x - i;
        float p = v * (1 - s);
        float q = v * (1 - s * f);
        float t = v * (1 - s * (1 - f));
        if(i == 0) set(v, t, p);
        if(i == 1) set(q, v, p);
        if(i == 2) set(p, v, t);
        if(i == 3) set(p, q, v);
        if(i == 4) set(t, p, v);
        if(i == 5) set(v, p, q);
        return clip();
    }

    public float[] hsv(){
        float max = max(r, g, b);
        float min = min(r, g, b);
        float range = max - min;
        float[] hsv = new float[3];
        if(range == 0) hsv[0] = 0;
        else if(max == r) hsv[0] = (60 * (g - b) / range + 360) % 360;
        else if(max == g) hsv[0] = 60 * (b - r) / range + 120;
        else hsv[0] = 60 * (r - g) / range + 240;
        if(max > 0) hsv[1] = 1 - min / max;
        else hsv[1] = 0;
        hsv[2] = max;
        return hsv;
    }

    public Color rgba8888(int v){
        r = ((v & 0xff000000) >>> 24) / 255f;
        g = ((v & 0x00ff0000) >>> 16) / 255f;
        b = ((v & 0x0000ff00) >>> 8) / 255f;
        a = ((v & 0x000000ff)) / 255f;
        return this;
    }

    public int rgba8888(){
        return ((int)(r * 255) << 24) | ((int)(g * 255) << 16) | ((int)(b * 255) << 8) | (int)(a * 255);
    }

    public Color add(float v){
        r += v;
        g += v;
        b += v;
        return this;
    }

    public Color scl(float v){
        r *= v;
        g *= v;
        b *= v;
        return this;
    }

    public Color nor(){
        float max = max(r, g, b);
        return scl(1f / max);
    }

    public Color inv(){
        return scl(-1).add(1);
    }

    public Color clip(){
        r = clamp(r, 0, 1);
        g = clamp(g, 0, 1);
        b = clamp(b, 0, 1);
        a = clamp(a, 0, 1);
        return this;
    }

    public Color cpy(){
        return new Color(this);
    }
}
