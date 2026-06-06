package examples;

import jbsae.core.*;
import jbsae.core.loop.*;
import jbsae.graphics.*;
import jbsae.math.*;
import jbsae.struct.*;
import jbsae.util.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Mathf.*;

// I originally wrote this in python (pygame), and had ChatGPT port it to java for me.
// Thanks ChatGPT :P

public class Minesweeper extends Screen{
    // Game Settings
    public static final int GRID_SIZE = 10;
    public static final int MINES = 15;

    // UI/Graphics
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final float TILE_SIZE = 40;
    public static final float TILE_DISTANCE = 50;
    public static final float SHADOW_OFFSET_X = -3;
    public static final float SHADOW_OFFSET_Y = 3;

    // Colors
    public static final Color BG_COLOR = new Color(240 / 255f, 230 / 255f, 220 / 255f, 1f);
    public static final Color TILE_COLOR = new Color(170 / 255f, 170 / 255f, 170 / 255f, 1f);
    public static final Color FLAG_COLOR = new Color(120 / 255f, 120 / 255f, 120 / 255f, 1f);
    public static final Color UI_TEXT_COLOR = new Color(80 / 255f, 80 / 255f, 80 / 255f, 1f);
    public static final Color SHADOW_COLOR = new Color(0f, 0f, 0f, 40 / 255f);
    public static final Color HIGHLIGHT_COLOR = new Color(1f, 1f, 1f, 40 / 255f);

    // Animations / Timings
    public static final float CHAIN_REVEAL_WAIT = 6f;
    public static final float LOSE_SCREEN_SHAKE = 10f;
    public static final float LOSE_SHAKE_TIME = 15f;
    public static final float LOSE_REVEAL_INITIAL_PERIOD = 60f;
    public static final float LOSE_REVEAL_PERIOD_DECAY = 0.8f;
    public static final float FADE_SCREEN_DELAY = 60f;
    public static final float FADE_SCREEN_TIME = 240f;
    public static final float FADE_SCREEN_OPACITY = 0.2f;
    public static final float GRAVITY = 0.5f;

    // Utility Constants
    public static final int[] D4X = {1, 0, -1, 0};
    public static final int[] D4Y = {0, 1, 0, -1};
    public static final int[] D8X = {1, 1, 0, -1, -1, -1, 0, 1};
    public static final int[] D8Y = {0, 1, 1, 1, 0, -1, -1, -1};

    // Global State
    private Tile[][] grid;
    private Seq<Event> scheduled;
    private Seq<Effect> effects;
    private int flaggedMines = 0;
    private boolean minesGenerated = false;
    private boolean win = false;
    private boolean lose = false;

    private float translateX = 0;
    private float translateY = 0;

    // --- Interfaces and Classes ---

    private interface Effect{
        void update();

        void draw();

        boolean keep();
    }

    private class Event{
        float ticks;
        Runnable func;

        Event(float ticks, Runnable func){
            this.ticks = ticks;
            this.func = func;
        }

        void update(){
            ticks -= time.delta;
            if(ticks <= 0) func.run();
        }

        boolean keep(){
            return ticks > 0;
        }
    }

    private class Tile{
        boolean mine = false;
        boolean revealed = false;
        boolean flagged = false;
        int x, y;
        float dx, dy;
        int count = 0;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
            Vec2 screenPos = tileToScreen(x, y);
            this.dx = screenPos.x;
            this.dy = screenPos.y;
        }

        void placeMine(){
            this.mine = true;
            for(int r = 0;r < 8;r++){
                int nx = this.x + D8X[r];
                int ny = this.y + D8Y[r];
                if(inGrid(nx, ny)){
                    grid[nx][ny].count++;
                }
            }
        }

        void flag(){
            flaggedMines += this.flagged ? -1 : 1;
            this.flagged = !this.flagged;
            if(flaggedMines == MINES) checkWin();
        }

        void chain4(){
            for(int r = 0;r < 4;r++){
                int nx = this.x + D4X[r];
                int ny = this.y + D4Y[r];
                if(inGrid(nx, ny)) grid[nx][ny].uncover();
            }
            scheduled.add(new Event(CHAIN_REVEAL_WAIT, this::chain8));
        }

        void chain8(){
            for(int r = 0;r < 8;r++){
                int nx = this.x + D8X[r];
                int ny = this.y + D8Y[r];
                if(inGrid(nx, ny)) grid[nx][ny].uncover();
            }
        }

        void solve(){
            if(this.mine){
                this.flagged = true;
            }else{
                this.revealed = true;
                effects.add(new FallingTile(this.dx, this.dy));
            }
        }

        boolean solved(){
            return (this.mine && this.flagged) || (!this.mine && this.revealed);
        }

        void uncover(){
            if(!minesGenerated){
                genMines(this.x, this.y);
                minesGenerated = true;
            }

            if(this.mine){
                lose = true;
                effects.add(new ScreenShake(LOSE_SCREEN_SHAKE, LOSE_SHAKE_TIME));
                scheduled.add(new Event(LOSE_REVEAL_INITIAL_PERIOD, () -> effects.add(new RevealTile(LOSE_REVEAL_INITIAL_PERIOD))));
                return;
            }

            if(!this.flagged && !this.revealed){
                this.revealed = true;
                effects.add(new FallingTile(this.dx, this.dy));
                if(this.count == 0){
                    scheduled.add(new Event(CHAIN_REVEAL_WAIT, this::chain4));
                }
            }

            if(flaggedMines == MINES) checkWin();
        }

        void update(float mx, float my){
            if(!win && !lose && over(mx, my, this.dx, this.dy, TILE_SIZE, TILE_SIZE) && !this.revealed){
                if(input.clicking[0] && !this.flagged) this.uncover();
                else if(input.clicking[1]){
                    this.flag();
                    input.clicking[1] = false;
                }
            }
        }

        void draw(float mx, float my){
            boolean hover = over(mx, my, this.dx, this.dy, TILE_SIZE, TILE_SIZE);

            if(this.revealed){
                if(this.count > 0){
                    Drawf.fill(TILE_COLOR.r, TILE_COLOR.g, TILE_COLOR.b, 1f);
                    Drawf.text(String.valueOf(this.count), this.dx + translateX, this.dy + translateY - 15, 30);

                    // Right-click highlight on revealed cells
                    if(hover && input.clicking[1]){
                        Drawf.layer(1f);
                        for(int r = 0;r < 8;r++){
                            int nx = this.x + D8X[r];
                            int ny = this.y + D8Y[r];
                            if(inGrid(nx, ny) && !grid[nx][ny].revealed){
                                drawCenteredRect(grid[nx][ny].dx, grid[nx][ny].dy, TILE_SIZE, TILE_SIZE, HIGHLIGHT_COLOR);
                            }
                        }
                        Drawf.layer(0f);
                    }
                }
            }else{
                // Shadow
                drawCenteredRect(this.dx + SHADOW_OFFSET_X, this.dy + SHADOW_OFFSET_Y, TILE_SIZE, TILE_SIZE, SHADOW_COLOR);
                // Tile
                drawCenteredRect(this.dx, this.dy, TILE_SIZE, TILE_SIZE, this.flagged ? FLAG_COLOR : TILE_COLOR);
                // Highlight
                if(hover){
                    drawCenteredRect(this.dx, this.dy, TILE_SIZE, TILE_SIZE, HIGHLIGHT_COLOR);
                }
            }
        }
    }

    // --- Effects Implementations ---
    private class ScreenShake implements Effect{
        float shake, ticks;

        ScreenShake(float shake, float ticks){
            this.shake = shake;
            this.ticks = ticks;
        }

        public void update(){
            translateX = random(-shake, shake);
            translateY = random(-shake, shake);
            ticks -= time.delta;
        }

        public void draw(){
        }

        public boolean keep(){
            return ticks > 0;
        }
    }

    private class RevealTile implements Effect{
        float ticks;

        RevealTile(float ticks){
            this.ticks = ticks;
        }

        public void update(){
            Seq<Vec2> possible = new Seq<>();
            for(int x = 0;x < GRID_SIZE;x++){
                for(int y = 0;y < GRID_SIZE;y++){
                    if(!grid[x][y].solved()) possible.add(new Vec2(x, y));
                }
            }

            if(possible.size == 0){
                scheduled.add(new Event(FADE_SCREEN_DELAY, () -> effects.add(
                new FadeScreen(BG_COLOR, FADE_SCREEN_TIME * (1 + FADE_SCREEN_OPACITY), FADE_SCREEN_OPACITY * FADE_SCREEN_TIME, "YOU LOSE")
                )));
                return;
            }

            Vec2 selected = possible.get((int)random(0, possible.size - 1));
            grid[(int)selected.x][(int)selected.y].solve();

            float nextTicks = this.ticks * LOSE_REVEAL_PERIOD_DECAY;
            scheduled.add(new Event(nextTicks, () -> effects.add(new RevealTile(nextTicks))));
        }

        public void draw(){
        }

        public boolean keep(){
            return false;
        }
    }

    private class FallingTile implements Effect{
        float x, y, r, xVel, yVel, rVel;

        FallingTile(float x, float y){
            this.x = x;
            this.y = y;
            this.xVel = (random(0, 1) - 0.5f) * TILE_SIZE / 4f;
            this.yVel = (random(0, 1) - 0.6f) * TILE_SIZE / 8f;
            this.rVel = (random(0, 1) - 0.5f) * TILE_SIZE / 2f;
        }

        public void update(){
            this.x += this.xVel * time.delta;
            this.y += this.yVel * time.delta;
            this.r += this.rVel * time.delta;
            this.yVel -= GRAVITY * time.delta;
        }

        public void draw(){
            drawCenteredRect(this.x + SHADOW_OFFSET_X, this.y + SHADOW_OFFSET_Y, TILE_SIZE, TILE_SIZE, SHADOW_COLOR, this.rVel);
            drawCenteredRect(this.x, this.y, TILE_SIZE, TILE_SIZE, TILE_COLOR, this.r);
        }

        public boolean keep(){
            return this.y <= SCREEN_HEIGHT + TILE_SIZE;
        }
    }

    private class FadeScreen implements Effect{
        Color color;
        float total, ticks, end;
        String text;

        FadeScreen(Color color, float ticks, float end, String text){
            this.color = color;
            this.total = ticks;
            this.ticks = ticks;
            this.end = end;
            this.text = text;
        }

        public void update(){
            if(this.ticks > this.end) this.ticks -= time.delta;
        }

        public void draw(){
            float alpha = clamp(1f - (this.ticks / this.total), 0f, 1f);
            drawCenteredRect(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, SCREEN_WIDTH + 5, SCREEN_HEIGHT + 5,
            new Color(color.r, color.g, color.b, alpha));

            if(this.ticks <= this.end){
                Drawf.fill(UI_TEXT_COLOR.r, UI_TEXT_COLOR.g, UI_TEXT_COLOR.b, 1f);
                Drawf.text(this.text, SCREEN_WIDTH / 2f + translateX, SCREEN_HEIGHT / 2f + translateY, 40);
            }
        }

        public boolean keep(){
            return true;
        }
    }

    // --- Core Methods ---

    @Override
    public void init(){
        load();

        // Attempt to load standard font from your framework
        Drawf.font(assets.fonts.get("brandbe.fnt"));
        reset();
    }

    public void reset(){
        grid = new Minesweeper.Tile[GRID_SIZE][GRID_SIZE];
        for(int x = 0;x < GRID_SIZE;x++){
            for(int y = 0;y < GRID_SIZE;y++){
                grid[x][y] = new Tile(x, y);
            }
        }
        scheduled = new Seq<>();
        effects = new Seq<>();
        flaggedMines = 0;
        minesGenerated = false;
        win = false;
        lose = false;
        translateX = 0;
        translateY = 0;
    }

    public void genMines(int cx, int cy){
        Seq<Vec2> possible = new Seq<>();
        for(int x = 0;x < GRID_SIZE;x++){
            for(int y = 0;y < GRID_SIZE;y++){
                possible.add(new Vec2(x, y));
            }
        }

        // Remove clicked and surrounding
        for(int i = possible.size - 1;i >= 0;i--){
            Vec2 p = possible.get(i);
            if(p.x == cx && p.y == cy){
                possible.remove(i);
                continue;
            }
            for(int r = 0;r < 8;r++){
                if(p.x == cx + D8X[r] && p.y == cy + D8Y[r]){
                    possible.remove(i);
                    break;
                }
            }
        }

        // Shuffle & pick
        for(int i = 0;i < MINES;i++){
            int o = randInt(0, possible.size);
            Vec2 spot = possible.get(o);
            grid[(int)spot.x][(int)spot.y].placeMine();
            possible.remove(o);
        }
    }

    public void checkWin(){
        for(int x = 0;x < GRID_SIZE;x++){
            for(int y = 0;y < GRID_SIZE;y++){
                if((grid[x][y].mine && !grid[x][y].flagged) || (!grid[x][y].mine && !grid[x][y].revealed)){
                    return;
                }
            }
        }
        win = true;
        scheduled.add(new Event(FADE_SCREEN_DELAY, () -> effects.add(
        new FadeScreen(BG_COLOR, FADE_SCREEN_TIME * (1 + FADE_SCREEN_OPACITY), FADE_SCREEN_OPACITY * FADE_SCREEN_TIME, "YOU WIN")
        )));
    }

    @Override
    public void update(){
        if(input.pressed.contains('R')){
            reset();
        }

        // Mouse inputs
        float mx = input.mouse.x;
        float my = input.mouse.y;

        // Process grid interactions
        for(int x = 0;x < GRID_SIZE;x++){
            for(int y = 0;y < GRID_SIZE;y++){
                grid[x][y].update(mx, my);
            }
        }

        // Manage events
        Seq<Event> newScheduled = new Seq<>();
        for(Event event : scheduled){
            event.update();
            if(event.keep()) newScheduled.add(event);
        }
        scheduled = newScheduled;

        // Manage effects
        Seq<Effect> newEffects = new Seq<>();
        for(Effect effect : effects){
            effect.update();
            if(effect.keep()) newEffects.add(effect);
        }
        effects = newEffects;
    }

    @Override
    public void draw(){
        // Clear background
        assets.textures.get("square.png").bind();
        Drawf.fill(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, BG_COLOR.a);
        Drawf.rect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        float mx = input.mouse.x;
        float my = input.mouse.y;

        // Draw basic tiles
        for(int x = 0;x < GRID_SIZE;x++){
            for(int y = 0;y < GRID_SIZE;y++){
                grid[x][y].draw(mx, my);
            }
        }

        // Draw falling effects and fades
        for(Effect effect : effects){
            effect.draw();
        }

        // Draw UI
        Drawf.fill(UI_TEXT_COLOR.r, UI_TEXT_COLOR.g, UI_TEXT_COLOR.b, UI_TEXT_COLOR.a);
        Drawf.text("Remaining: " + (MINES - flaggedMines), 10, 10, 30);
    }

    // --- Helpers ---

    private void drawCenteredRect(float x, float y, float w, float h, Color color){
        drawCenteredRect(x, y, w, h, color, 0);
    }

    private void drawCenteredRect(float x, float y, float w, float h, Color color, float rot){
        Drawf.fill(color.r, color.g, color.b, color.a);
        Drawf.drawc(assets.textures.get("square.png").full, x + translateX, y + translateY, w, h, rot);
    }

    private Vec2 tileToScreen(int x, int y){
        return new Vec2(
        (float)(((x - GRID_SIZE / 2.0 + 0.5) * TILE_DISTANCE) + SCREEN_WIDTH / 2.0),
        (float)(((y - GRID_SIZE / 2.0 + 0.5) * TILE_DISTANCE) + SCREEN_HEIGHT / 2.0)
        );
    }

    private boolean inGrid(int x, int y){
        return x >= 0 && y >= 0 && x < GRID_SIZE && y < GRID_SIZE;
    }

    private boolean over(float px, float py, float bx, float by, float bw, float bh){
        return px >= bx - bw / 2 && py >= by - bh / 2 && px <= bx + bw / 2 && py <= by + bh / 2;
    }

    public static void main(String[] args){
        width = SCREEN_WIDTH;
        height = SCREEN_HEIGHT;
        loop = new DynamicLoop();
        jbsae(new Minesweeper());
    }
}