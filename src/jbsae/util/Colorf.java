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
    violet = new Color(1, 0, 1),
    magenta = new Color(1, 0, 0.5f),
    orange = new Color(1, 0.5f, 0),
    lime = new Color(0.5f, 1, 0),
    turquoise = new Color(0, 1, 0.5f),
    blue = new Color(0, 0.5f, 1),
    purple = new Color(0.5f, 0, 1),

    lightRed = new Color(1, 0.5f, 0.5f),
    lightYellow = new Color(1, 1, 0.5f),
    lightGreen = new Color(0.5f, 1, 0.5f),
    lightCyan = new Color(0.5f, 1, 1),
    lightNavy = new Color(0.5f, 0.5f, 1),
    lightViolet = new Color(1, 0.5f, 1),
    lightMagenta = new Color(1, 0.5f, 0.75f),
    lightOrange = new Color(1, 0.75f, 0.5f),
    lightLime = new Color(0.75f, 1, 0.5f),
    lightTurquoise = new Color(0.5f, 1, 0.75f),
    lightBlue = new Color(0.5f, 0.75f, 1),
    lightPurple = new Color(0.75f, 0.5f, 1),

    darkRed = new Color(0.5f, 0, 0),
    darkYellow = new Color(0.5f, 0.5f, 0),
    darkGreen = new Color(0, 0.5f, 0),
    darkCyan = new Color(0, 0.5f, 0.5f),
    darkNavy = new Color(0, 0, 0.5f),
    darkViolet = new Color(0.5f, 0, 0.5f),
    darkMagenta = new Color(0.5f, 0, 0.25f),
    darkOrange = new Color(0.5f, 0.25f, 0),
    darkLime = new Color(0.25f, 0.5f, 0),
    darkTurquoise = new Color(0, 0.5f, 0.25f),
    darkBlue = new Color(0, 0.25f, 0.5f),
    darkPurple = new Color(0.25f, 0, 0.5f);

    public static Color createRandom(){
        return new Color(random(), random(), random());
    }

    public static Color createVibrant(){
        return new Color(random(0, 360), random(), random());
    }
}
