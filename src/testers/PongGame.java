package testers;

import jbsae.*;
import jbsae.core.*;
import jbsae.math.*;
import jbsae.struct.*;
import jbsae.util.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Mathf.*;

public class PongGame extends Screen{
    // Constants
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int PADDLE_WIDTH = 10;
    public static final int PADDLE_HEIGHT = 80;
    public static final float PADDLE_MAX_SPEED = 8;
    public static final float PADDLE_ACCELERATION = 1;
    public static final float PADDLE_FRICTION = 0.9f;
    public static final int BALL_SIZE = 10;
    public static final int BALL_SPEED = 5;
    public static final int TRAIL_LENGTH = 20;  // Length of the trail
    public static final int BALL_COUNT = 3;     // Number of balls
    public static final float ADD_BALL_INTERVAL = 10; // Time interval to add new balls (in seconds)

    // Game objects
    private Range2 paddle1;
    private Range2 paddle2;
    private float paddle1Velocity;
    private float paddle2Velocity;

    private Seq<Ball> balls;
    private float elapsedTime;
    private float lastBallAddedTime;

    // Scores
    private int score1;
    private int score2;

    private class Ball{
        Vec2 position;
        Vec2 velocity;
        Queue<Vec2> trail;

        Ball(float x, float y, float vx, float vy){
            position = new Vec2(x, y);
            velocity = new Vec2(vx, vy);
            trail = new Queue<>();
        }

        void update(){
            position.x += velocity.x;
            position.y += velocity.y;

            // Add current ball position to trail
            trail.addFirst(position.cpy());
            if(trail.size > TRAIL_LENGTH){
                trail.removeLast();
            }

            // Check collision with paddles
            if(paddle1.contains(position) || paddle2.contains(position)){
                float currentSpeed = velocity.len();
                velocity.x *= -1;
                velocity.nor().scl(currentSpeed + 0.5f);  // Increase speed slightly
            }

            // Check collision with top and bottom walls
            if(position.y <= 0 || position.y + BALL_SIZE >= HEIGHT){
                velocity.y *= -1;
            }

            // Check for scoring
            if(position.x <= 0){
                score2++;
                reset();
            }else if(position.x + BALL_SIZE >= WIDTH){
                score1++;
                reset();
            }
        }

        void reset(){
            position.set(WIDTH / 2, HEIGHT / 2);
            velocity.set(Tmp.v1.set(1, 0).rot(random(0, 360)).scl(BALL_SPEED));
            trail.clear();
        }

        void draw(){
            // Draw ball trail
            int alphaStep = 255 / TRAIL_LENGTH;
            for(int i = 0;i < trail.size;i++){
                Vec2 pos = trail.get(i);
                Drawf.fill(1f, 1f, 1f, (alphaStep * (TRAIL_LENGTH - i)) / 255f);
                Drawf.rect(pos.x, pos.y, BALL_SIZE, BALL_SIZE);
            }

            // Draw ball
            Drawf.fill(1f, 1f, 1f);
            Drawf.rect(position.x, position.y, BALL_SIZE, BALL_SIZE);
        }
    }

    @Override
    public void init(){

        JBSAE.load();

        Drawf.font(assets.fonts.get("brandbe.fnt"));

        paddle1 = new Range2(20, HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle2 = new Range2(WIDTH - 30, HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle1Velocity = 0;
        paddle2Velocity = 0;

        balls = new Seq<>();
        for(int i = 0;i < BALL_COUNT;i++){
            balls.add(new Ball(WIDTH / 2, HEIGHT / 2, Tmp.v1.set(1, 0).rot(random(0, 360)).scl(BALL_SPEED).x, Tmp.v1.y));
        }

        elapsedTime = 0;
        lastBallAddedTime = 0;
    }

    @Override
    public void update(){
        // Apply acceleration to paddle1
        if(JBSAE.input.pressed.contains('W')){
            paddle1Velocity += PADDLE_ACCELERATION;
        }else if(JBSAE.input.pressed.contains('S')){
            paddle1Velocity -= PADDLE_ACCELERATION;
        }else{
            // Apply friction to paddle1
            paddle1Velocity *= PADDLE_FRICTION;
        }

        // Apply acceleration to paddle2 (AI controlled)
        if(ball().y > paddle2.y + PADDLE_HEIGHT / 2){
            paddle2Velocity += PADDLE_ACCELERATION;
        }else if(ball().y < paddle2.y + PADDLE_HEIGHT / 2){
            paddle2Velocity -= PADDLE_ACCELERATION;
        }else{
            // Apply friction to paddle2
            paddle2Velocity *= PADDLE_FRICTION;
        }

        // Clamp velocities
        paddle1Velocity = clamp(paddle1Velocity, -PADDLE_MAX_SPEED, PADDLE_MAX_SPEED);
        paddle2Velocity = clamp(paddle2Velocity, -PADDLE_MAX_SPEED, PADDLE_MAX_SPEED);

        // Update paddle positions
        paddle1.y += paddle1Velocity;
        paddle2.y += paddle2Velocity;

        // Ensure paddles stay within bounds
        paddle1.y = clamp(paddle1.y, 0, HEIGHT - PADDLE_HEIGHT);
        paddle2.y = clamp(paddle2.y, 0, HEIGHT - PADDLE_HEIGHT);

        // Update balls
        for(Ball ball : balls){
            ball.update();
        }

        // Update elapsed time
        elapsedTime += JBSAE.time.delta / JBSAE.fps;

        // Add new ball every ADD_BALL_INTERVAL seconds
        if(elapsedTime - lastBallAddedTime >= ADD_BALL_INTERVAL){
            balls.add(new Ball(WIDTH / 2, HEIGHT / 2, Tmp.v1.set(1, 0).rot(random(0, 360)).scl(BALL_SPEED).x, Tmp.v1.y));
            lastBallAddedTime = elapsedTime;
        }
    }

    private Vec2 ball(){
        Ball rightmostBall = balls.get(0);
        for(int i = 1;i < balls.size;i++){
            if(balls.get(i).position.x > rightmostBall.position.x){
                rightmostBall = balls.get(i);
            }
        }
        return rightmostBall.position;
    }

    @Override
    public void draw(){
        assets.textures.get("square.png").bind();

        Drawf.fill(0.1f, 0.1f, 0.1f);
        Drawf.rect(0, 0, WIDTH, HEIGHT);

        // Draw paddles
        Drawf.fill(1f, 1f, 1f);
        Drawf.rect(paddle1.x, paddle1.y, paddle1.w, paddle1.h);
        Drawf.rect(paddle2.x, paddle2.y, paddle2.w, paddle2.h);

        // Draw balls
        for(Ball ball : balls){
            ball.draw();
        }

        // Draw scores
        Drawf.fill(0.8f, 0.8f, 0.8f);
        Drawf.text(score1 + " - " + score2, WIDTH / 2, 50);
    }

    public static void main(String[] args){
        JBSAE.width = WIDTH;
        JBSAE.height = HEIGHT;
        jbsae(new PongGame());
    }
}
