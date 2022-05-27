package jbsae.graphics;

public class Glyph{
    public Font font;
    public int id, x, y, width, height, xOffset, yOffset, xAdvance, page, channel;
    public Region region;

    public Glyph(Font font){
        this.font = font;
    }

    public void load(){
        region = new Region(font.pages.get(page), x, y, width, height);
    }
}
