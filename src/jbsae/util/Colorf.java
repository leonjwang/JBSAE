package jbsae.util;

import jbsae.graphics.*;

import static jbsae.util.Mathf.*;

public class Colorf{
    public static final Color
    clear = new Color(0, 0, 0, 0),
    white = new Color(1, 1, 1),
    gray = new Color(0.5f, 0.5f, 0.5f),
    black = new Color(0, 0, 0),

    red = new Color(1, 0, 0),
    yellow = new Color(1, 1, 0),
    green = new Color(0, 1, 0),
    cyan = new Color(0, 1, 1),
    navy = new Color(0, 0, 1),
    purple = new Color(1, 0, 1),
    magenta = new Color(1, 0, 0.5f),
    orange = new Color(1, 0.5f, 0),
    lime = new Color(0.5f, 1, 0),
    turquoise = new Color(0, 1, 0.5f),
    blue = new Color(0, 0.5f, 1),
    violet = new Color(0.5f, 0, 1),

    lightRed = lighten(red.cpy()),
    lightYellow = lighten(yellow.cpy()),
    lightGreen = lighten(green.cpy()),
    lightCyan = lighten(cyan.cpy()),
    lightNavy = lighten(navy.cpy()),
    lightPurple = lighten(purple.cpy()),
    lightMagenta = lighten(magenta.cpy()),
    lightOrange = lighten(orange.cpy()),
    lightLime = lighten(lime.cpy()),
    lightTurquoise = lighten(turquoise.cpy()),
    lightBlue = lighten(blue.cpy()),
    lightViolet = lighten(violet.cpy()),

    darkRed = darken(red.cpy()),
    darkYellow = darken(yellow.cpy()),
    darkGreen = darken(green.cpy()),
    darkCyan = darken(cyan.cpy()),
    darkNavy = darken(navy.cpy()),
    darkPurple = darken(purple.cpy()),
    darkMagenta = darken(magenta.cpy()),
    darkOrange = darken(orange.cpy()),
    darkLime = darken(lime.cpy()),
    darkTurquoise = darken(turquoise.cpy()),
    darkBlue = darken(blue.cpy()),
    darkViolet = darken(violet.cpy());

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
        return new Color(random(), random(), random());
    }

    public static Color vibrantc(){
        return new Color(random(0, 360), random(), random());
    }
}
