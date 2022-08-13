package jbsae.math.interp;


public class Bounce implements Interp{
    public float[] x, y;

    public Bounce(int bounces){
        if(bounces < 2 || bounces > 5) return;
        x = new float[bounces];
        y = new float[bounces];
        y[0] = 1;
        switch(bounces){
            case 2:
                x[0] = 0.6f;
                x[1] = 0.4f;
                y[1] = 0.33f;
                break;
            case 3:
                x[0] = 0.4f;
                x[1] = 0.4f;
                x[2] = 0.2f;
                y[1] = 0.33f;
                y[2] = 0.1f;
                break;
            case 4:
                x[0] = 0.34f;
                x[1] = 0.34f;
                x[2] = 0.2f;
                x[3] = 0.15f;
                y[1] = 0.26f;
                y[2] = 0.11f;
                y[3] = 0.03f;
                break;
            case 5:
                x[0] = 0.3f;
                x[1] = 0.3f;
                x[2] = 0.2f;
                x[3] = 0.1f;
                x[4] = 0.1f;
                y[1] = 0.45f;
                y[2] = 0.3f;
                y[3] = 0.15f;
                y[4] = 0.06f;
                break;
        }
        x[0] *= 2;
    }

    private float out(float f){
        float test = f + x[0] / 2;
        if(test < x[0]) return test / (x[0] / 2) - 1;

        if(f == 1) return 1;
        f += x[0] / 2;
        float width = 0, height = 0;
        for(int i = 0, n = x.length;i < n;i++){
            width = x[i];
            if(f <= width){
                height = y[i];
                break;
            }
            f -= width;
        }
        f /= width;
        float z = 4 / width * height * f;
        return 1 - (z - z * f) * width;
    }

    @Override
    public float get(float f){
        if(f <= 0.5f) return (1 - out(1 - f * 2)) / 2;
        return out(f * 2 - 1) / 2 + 0.5f;
    }
}
