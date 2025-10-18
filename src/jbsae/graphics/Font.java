package jbsae.graphics;

import jbsae.graphics.gl.*;
import jbsae.struct.*;
import jbsae.struct.prim.*;

import static jbsae.JBSAE.*;

public class Font{
    public String name;

    public Glyph none;
    public Texture[] page;
    public IntMap<Glyph> glyphs = new IntMap<>();

    public int size, unicode, stretchH, smooth, aa, padLeft, padRight, padTop, padBot, spacing, outline, pages;
    public boolean bold, italic;
    public String charset;

    public Font(){
        assets.fontList.add(this);
    }
}
