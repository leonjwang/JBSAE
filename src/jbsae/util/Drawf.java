package jbsae.util;

import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.math.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Colorf.*;

public class Drawf{
    public static Color fill;

    public static Range2 tr = new Range2();
    public static Shape2 ts1 = new Shape2(4), ts2 = new Shape2(4);

    public static void rect(float x, float y, float w, float h){
        renderer.draw(tr.set(x, y, w, h).toShape(ts1), tr.set(0, 0, 1, 1).toShape(ts2), white);
    }
}
