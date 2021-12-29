package jbsae.util;

import jbsae.math.interp.*;

import static jbsae.util.Mathf.*;

/** @author Nathan Sweet */
public class Interpf{
    public static final Interp
    lin = f -> f,
    pow2 = f -> pow(f, 2),
    pow3 = f -> pow(f, 3),
    pow5 = f -> pow(f, 5),
    rt2 = f -> rt(f, 2),
    rt3 = f -> rt(f, 3),
    rt5 = f -> rt(f, 5),
    sin = f -> sin(f * 180 - 90) / 2 + 0.5f,
    sint = f -> sin(f * 90),
    sinb = f -> sin(f * 90 - 90) + 1,
    smooth = f -> pow(f, 2) * (3 - f * 2),
    smoother = f -> pow(f, 3) * (f * (f * 6 - 15) + 10),
    pow3m = f -> pow((f - 0.5f) * 2, 3) / 2 + 0.5f,
    pow5m = f -> pow((f - 0.5f) * 2, 5) / 2 + 0.5f,
    spow2 = new Pow(2),
    spow3 = new Pow(3),
    spow5 = new Pow(5),
    exp5 = new Exp(2, 5),
    exp5t = f -> (exp5.get(f / 2f + 0.5f) - 0.5f) * 2f,
    exp5b = f -> exp5.get(f / 2f) * 2f,
    exp10 = new Exp(2, 10),
    exp10t = f -> (exp10.get(f / 2f + 0.5f) - 0.5f) * 2f,
    exp10b = f -> exp10.get(f / 2f) * 2f,
    circle = new Circle(),
    circlet = f -> (circle.get(f / 2f + 0.5f) - 0.5f) * 2f,
    circleb = f -> circle.get(f / 2f) * 2f,
    elastic = new Elastic(2, 10, 7, 1),
    elastict = f -> (elastic.get(f / 2f + 0.51f) - 0.5f) * 2f,
    elasticb = f -> min(elastic.get(f / 2f + 0.01f) * 2f, 1),
    bounce = new Bounce(4),
    bouncet = f -> (bounce.get(f / 2f + 0.5f) - 0.5f) * 2f,
    bounceb = f -> bounce.get(f / 2f) * 2f,
    swing = new Swing(1.5f),
    swingt = f -> (swing.get(f / 2f + 0.5f) - 0.5f) * 2f,
    swingb = f -> swing.get(f / 2f) * 2f;
}
