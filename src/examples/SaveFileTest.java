package examples;

import jbsae.*;
import jbsae.core.*;
import jbsae.files.saves.*;
import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.util.*;
import org.lwjgl.glfw.*;

import java.util.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Stringf.*;

public class SaveFileTest extends Screen{
    public Texture box;

    public static final String SAVE_FILE_HEADER = "SAVE FILE EXAMPLE HEADER";
    public static final float VERSION = 0.0f;

    public static final float pixelSize = 20;

    public static String saveFilename = null;

    public SaveFi file = null;


    public int width = 20, height = 20;
    public Color[][] grid;


    public void fillGrid(){
        for(int x = 0;x < width;x++){
            for(int y = 0;y < height;y++){
                grid[x][y] = new Color();
            }
        }
    }

    public void readFile(){
        Read read = file.read();
        if(read == null) throw new RuntimeException("File does not exist");

        String header = read.str();
        if(!header.equals(SAVE_FILE_HEADER)) throw new RuntimeException("Malformatted file header");

        float version = read.f();
        if(version > VERSION) throw new RuntimeException("Save version " + version + " > " + VERSION);
        Log.info("Save file version: " + version);

        width = read.i();
        height = read.i();
        Log.info("Image size: " + width + " x " + height);

        grid = new Color[width][height];
        fillGrid();
        for(int x = 0;x < width;x++){
            for(int y = 0;y < height;y++){
                grid[x][y].r = read.f();
                grid[x][y].g = read.f();
                grid[x][y].b = read.f();
                grid[x][y].a = read.f();
            }
        }
        read.close();
    }

    public void writeFile(){
        Write write = file.write();

        if(write == null) return; // Something has gone very wrong

        write.str(SAVE_FILE_HEADER);
        write.f(VERSION);
        write.i(width);
        write.i(height);
        for(int x = 0;x < width;x++){
            for(int y = 0;y < height;y++){
                write.f(grid[x][y].r);
                write.f(grid[x][y].g);
                write.f(grid[x][y].b);
                write.f(grid[x][y].a);
            }
        }
        write.close();
    }

    @Override
    public void init(){
        box = assets.textures.get("square.png");

        file = new SaveFi(saveFilename);

        try{
            readFile();
            Log.info("Loaded save " + saveFilename);
        }catch(Exception e){
            Log.error("Failed loading file: " + saveFilename);
            Log.error(getStackTrace(e));
            grid = new Color[width][height];
            fillGrid();
        }
    }

    @Override
    public void draw(){
        box.bind();
        Drawf.fill(Colorf.WHITE);


        float tx = JBSAE.width / 2f - (width / 2f * pixelSize);
        float ty = JBSAE.height / 2f - (height / 2f * pixelSize);
        for(int x = 0;x < width;x++){
            for(int y = 0;y < height;y++){
                Drawf.fill(grid[x][y]);
                Drawf.rect(tx + x * pixelSize, ty + y * pixelSize, pixelSize, pixelSize);

                Tmp.r1.set(tx + x * pixelSize, ty + y * pixelSize, pixelSize, pixelSize);
                if(Tmp.r1.contains(input.mouse)){
                    Drawf.fill(Colorf.WHITE);
                    Drawf.alpha(0.5f);
                    Drawf.rect(tx + x * pixelSize, ty + y * pixelSize, pixelSize, pixelSize);

                    if(input.clicking[0]){
                        grid[x][y].set(1f, 1f, 1f);
                    }
                }
            }
        }
    }

    @Override
    public void update(){
        if(input.pressed.contains(GLFW.GLFW_KEY_S)){
            writeFile();
            input.pressed.clear();
            Log.info("File saved");
        }
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your savefile name: ");
        saveFilename = scanner.nextLine();
        jbsae(new SaveFileTest());
    }
}
