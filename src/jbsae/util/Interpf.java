package jbsae.util;

import jbsae.math.interp.*;

import static jbsae.util.Mathf.*;


public class Interpf{
    public static final Interp
    LIN = f -> f,
    POW2 = f -> pow(f, 2),
    POW3 = f -> pow(f, 3),
    POW5 = f -> pow(f, 5),
    RT2 = f -> rt(f, 2),
    RT3 = f -> rt(f, 3),
    RT5 = f -> rt(f, 5),
    SIN = f -> sin(f * 180 - 90) / 2 + 0.5f,
    SINT = f -> sin(f * 90),
    SINB = f -> sin(f * 90 - 90) + 1,
    SMOOTH = f -> pow(f, 2) * (3 - f * 2),
    SMOOTHER = f -> pow(f, 3) * (f * (f * 6 - 15) + 10),
    POW3M = f -> pow((f - 0.5f) * 2, 3) / 2 + 0.5f,
    POW5M = f -> pow((f - 0.5f) * 2, 5) / 2 + 0.5f,
    SPOW2 = new Pow(2),
    SPOW3 = new Pow(3),
    SPOW5 = new Pow(5),
    EXP5 = new Exp(2, 5),
    EXP5T = f -> (EXP5.get(f / 2f + 0.5f) - 0.5f) * 2f,
    EXP5B = f -> EXP5.get(f / 2f) * 2f,
    EXP10 = new Exp(2, 10),
    EXP10T = f -> (EXP10.get(f / 2f + 0.5f) - 0.5f) * 2f,
    EXP10B = f -> EXP10.get(f / 2f) * 2f,
    CIRCLE = new Circle(),
    CIRCLET = f -> (CIRCLE.get(f / 2f + 0.5f) - 0.5f) * 2f,
    CIRCLEB = f -> CIRCLE.get(f / 2f) * 2f,
    ELASTIC = new Elastic(2, 10, 7, 1),
    ELASTICT = f -> (ELASTIC.get(f / 2f + 0.51f) - 0.5f) * 2f,
    ELASTICB = f -> min(ELASTIC.get(f / 2f + 0.01f) * 2f, 1),
    BOUNCE = new Bounce(4),
    BOUNCET = f -> (BOUNCE.get(f / 2f + 0.5f) - 0.5f) * 2f,
    BOUNCEB = f -> BOUNCE.get(f / 2f) * 2f,
    SWING = new Swing(1.5f),
    SWINGT = f -> (SWING.get(f / 2f + 0.5f) - 0.5f) * 2f,
    SWINGB = f -> SWING.get(f / 2f) * 2f;
}
