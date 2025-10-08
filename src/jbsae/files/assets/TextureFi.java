package jbsae.files.assets;

import jbsae.*;
import jbsae.graphics.gl.*;
import jbsae.util.*;

import java.io.*;
import java.nio.*;
import java.util.zip.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Stringf.*;

public class TextureFi extends AssetFi{
    public Texture texture;

    public TextureFi(String path){
        super(path);
    }

    @Override
    public TextureFi gen(){
        Log.info("Generating texture: " + path());
        try(InputStream stream = input()){
            PngReader reader = new PngReader();
            ByteBuffer buffer = reader.read(stream);
            texture = new Texture(reader.width, reader.height, buffer);
            assets.textures.add(name, texture);
        }catch(Exception e){
            Log.error("Failed to load texture: " + path());
            Log.error(getStackTrace(e));
        }

        return (TextureFi)super.gen();
    }

    /** Taken from Anuken/Arc */
    public static class PngReader{
        private static final int
        ctypeRgba = 6,
        ctypePalette = 3,
        ctypeRgb = 2;

        /** Size fields are set after reading. */
        public int width, height;

        public byte bitDepth, colorType, compression, filter, interlace;

        private int dataLen, cs;
        private byte[] imgData = null;
        private ByteBuffer buf = null;
        private int[] palette;

        public ByteBuffer read(InputStream in) throws IOException{
            readChunks(new DataInputStream(in));

            if(buf != null) buf.clear();
            buf = ByteBuffer.allocateDirect(cs);
            try{
                getImage(buf);
            }catch(DataFormatException e){
                throw new IOException(e);
            }
            buf.flip();
            return buf;
        }

        private void readChunks(DataInputStream in) throws IOException{
            if(imgData == null && in.available() > 4){
                long header = in.readLong(); //PNG signature
                if(header != 0x89504e470d0a1a0aL){
                    String headerString = Long.toHexString(header);
                    throw new IOException(headerString.startsWith("ffd8ff") ? "This is a JPEG, not a PNG." : "This isn't a PNG. Header: 0x" + headerString);
                }
            }else if(imgData == null){
                width = 0;
                return;
            }
            dataLen = 0;
            int chunkType;
            while(true){
                int chunkLen = in.readInt(); // Read the chunk length.
                if(chunkLen <= 0 || chunkLen > 99998192) break;

                chunkType = in.readInt();
                if(chunkType == 0x49454e44) //IEND
                    break; // last chunk reached..
                if(chunkType == 0x49444154){ //IDAT
                    in.readFully(imgData, dataLen, chunkLen);
                    dataLen += chunkLen;
                }else if(chunkType == 0x49484452){ //IHDR
                    width = in.readInt();
                    height = in.readInt();
                    bitDepth = in.readByte();
                    colorType = in.readByte();
                    compression = in.readByte();
                    filter = in.readByte();
                    interlace = in.readByte();

                    cs = 4 * width * height;
                    imgData = new byte[in.available()]; //initialize image array

                    //validation
                    if(bitDepth == 16) throw new IOException("16-bit depth is not supported.");
                    if(colorType == ctypePalette && bitDepth < 4) throw new IOException("Only PNG palettes with 4 or 8-bit depth are supported. Depth given: " + bitDepth);
                    if(colorType != ctypePalette && colorType != ctypeRgb && colorType != ctypeRgba) throw new IOException("Unsupported color type: " + colorType + " (Note that grayscale is not supported)");
                    if(interlace != 0) throw new IOException("PNG interlacing is not supported.");

                }else if(colorType == ctypePalette && chunkType == 0x504c5445){ //PLTE
                    int colors = chunkLen / 3;
                    palette = new int[colors];
                    for(int i = 0;i < colors;i++){
                        palette[i] = Colorf.pack(in.readUnsignedByte(), in.readUnsignedByte(), in.readUnsignedByte(), 255);
                    }
                }else if(colorType == ctypePalette && chunkType == 0x74524e53){ //tRNS
                    for(int i = 0;i < chunkLen;i++){
                        palette[i] = (palette[i] & 0xffffff00) | in.readUnsignedByte();
                    }
                }else{
                    byte[] chunkData = new byte[chunkLen];
                    in.readFully(chunkData);
                }
                in.readInt(); // checksum skip
            }
        }

        private void getImage(ByteBuffer bb) throws DataFormatException{
            //bpx bytes per pixel, wT total output width, v scanline width
            int
            bpx = colorType == ctypePalette ? 1 : colorType == ctypeRgb ? 3 : 4,
            wT = width * 4,
            v = (bitDepth == 4 ? width / 2 : width) * bpx + 1; // scanLine width

            Inflater inflater = new Inflater();
            inflater.setInput(imgData, 0, dataLen);

            byte[] prev = new byte[wT + 1], row = new byte[wT + 1]; // every row contains filter byte

            for(int i = 1, s = 0;s < height;i = 1, s++){ // scanLine
                //inflating each line is the bottleneck here, but unfortunately there's nothing I can do about it
                inflater.inflate(row, 0, v);
                byte first = row[0];

                if(first != 0){ //apply filters

                    if(first == 1){
                        for(i += bpx;i < v;i++){
                            row[i] += row[i - bpx];
                        }
                    }else if(first == 2){
                        for(;i < v;i++){
                            row[i] += prev[i];
                        }
                    }else if(first == 3){
                        for(;i < bpx + 1;i++){
                            row[i] += (prev[i] & 0xFF) >>> 1;
                        }
                        for(;i < v;i++){
                            row[i] += ((prev[i] & 0xFF) + (row[i - bpx] & 0xFF)) >>> 1;
                        }
                    }else{
                        for(;i < bpx + 1;i++){
                            row[i] += prev[i];
                        }
                        for(;i < v;i++){
                            row[i] += paeth(row[i - bpx] & 0xFF, prev[i] & 0xFF, prev[i - bpx] & 0xFF);
                        }
                    }
                }

                //format output, normal mode
                if(bpx == 3){
                    //this could probably made faster, but ehhh
                    ByteBuffer wRow = ByteBuffer.wrap(row);
                    for(i = 1;i < v;i += bpx){
                        bb.putInt((wRow.getInt(i) & 0xFFFFFF00) + 0xFF);
                    }
                }else if(bpx == 1){ //palette
                    //when bitDepth is 4, split every byte in two
                    if(bitDepth == 4){
                        for(i = 1;i < v;i += bpx){
                            bb.putInt(palette[Filef.left(row[i])]);
                            bb.putInt(palette[Filef.right(row[i])]);
                        }
                    }else{
                        for(i = 1;i < v;i += bpx){
                            bb.putInt(palette[row[i] & 0xFF]);
                        }
                    }
                }else{
                    bb.put(row, 1, v - 1);
                }
                byte[] swap = prev;
                prev = row;
                row = swap;
            }
            bb.position(bb.capacity());
            imgData = null;
        }

        private static int ab(int a){
            int b = a >> 8;
            return (a ^ b) - b;
        }

        private static int paeth(int a, int b, int c){
            int pa = b - c, pb = a - c, pc = ab(pa + pb);
            pa = ab(pa);
            pb = ab(pb);
            return (pa <= pb && pa <= pc) ? a : (pb <= pc) ? b : c;
        }
    }
}
