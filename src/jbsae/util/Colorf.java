package jbsae.util;

import jbsae.graphics.*;

import static jbsae.util.Mathf.*;

public class Colorf{
    public static final Color
    CLEAR = new Color(0, 0, 0, 0),
    WHITE = new Color(1, 1, 1),
    GRAY = new Color(0.5f, 0.5f, 0.5f),
    BLACK = new Color(0, 0, 0),

    RED = new Color(1, 0, 0),
    YELLOW = new Color(1, 1, 0),
    GREEN = new Color(0, 1, 0),
    CYAN = new Color(0, 1, 1),
    NAVY = new Color(0, 0, 1),
    PURPLE = new Color(1, 0, 1),
    MAGENTA = new Color(1, 0, 0.5f),
    ORANGE = new Color(1, 0.5f, 0),
    LIME = new Color(0.5f, 1, 0),
    TURQUOISE = new Color(0, 1, 0.5f),
    BLUE = new Color(0, 0.5f, 1),
    VIOLET = new Color(0.5f, 0, 1),

    LIGHT_RED = lighten(RED.cpy()),
    LIGHT_YELLOW = lighten(YELLOW.cpy()),
    LIGHT_GREEN = lighten(GREEN.cpy()),
    LIGHT_CYAN = lighten(CYAN.cpy()),
    LIGHT_NAVY = lighten(NAVY.cpy()),
    LIGHT_PURPLE = lighten(PURPLE.cpy()),
    LIGHT_MAGENTA = lighten(MAGENTA.cpy()),
    LIGHT_ORANGE = lighten(ORANGE.cpy()),
    LIGHT_LIME = lighten(LIME.cpy()),
    LIGHT_TURQUOISE = lighten(TURQUOISE.cpy()),
    LIGHT_BLUE = lighten(BLUE.cpy()),
    LIGHT_VIOLET = lighten(VIOLET.cpy()),

    DARK_RED = darken(RED.cpy()),
    DARK_YELLOW = darken(YELLOW.cpy()),
    DARK_GREEN = darken(GREEN.cpy()),
    DARK_CYAN = darken(CYAN.cpy()),
    DARK_NAVY = darken(NAVY.cpy()),
    DARK_PURPLE = darken(PURPLE.cpy()),
    DARK_MAGENTA = darken(MAGENTA.cpy()),
    DARK_ORANGE = darken(ORANGE.cpy()),
    DARK_LIME = darken(LIME.cpy()),
    DARK_TURQUOISE = darken(TURQUOISE.cpy()),
    DARK_BLUE = darken(BLUE.cpy()),
    DARK_VIOLET = darken(VIOLET.cpy());

    /** Color manipulation. */
    public static Color lighten(Color c){
        return lighten(c, 0.5f);
    }

    public static Color lighten(Color c, float p){
        return c.inv().scl(1f - p).inv();
    }


    public static Color darken(Color c){
        return darken(c, 0.5f);
    }

    public static Color darken(Color c, float p){
        return c.scl(1f - p);
    }


    /** Random creation. */
    public static Color randomc(){
        return randomc(new Color());
    }

    public static Color randomc(Color c){
        return c.set(random(), random(), random());
    }

    public static Color vibrantc(){
        return vibrantc(new Color());
    }

    public static Color vibrantc(Color c){
        return c.hsv(random(0, 360), 1f, 1f);
    }

    /** Bit packing. */
    public static int pack(int r, int g, int b, int a){
        return (r << 24) | (g << 16) | (b << 8) | (a);
    }
}
