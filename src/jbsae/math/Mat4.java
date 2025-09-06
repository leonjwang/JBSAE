package jbsae.math;

import org.lwjgl.system.*;

import java.nio.*;


// TODO: Complete
public class Mat4{
    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;

    public Mat4(){
        id();
    }


    public Mat4 id(){
        m00 = 1f;
        m11 = 1f;
        m22 = 1f;
        m33 = 1f;

        m01 = 0f;
        m02 = 0f;
        m03 = 0f;
        m10 = 0f;
        m12 = 0f;
        m13 = 0f;
        m20 = 0f;
        m21 = 0f;
        m23 = 0f;
        m30 = 0f;
        m31 = 0f;
        m32 = 0f;

        return this;
    }

    public Mat4 ortho(float l, float r, float b, float t, float n, float f){
        id();

        float tx = -(r + l) / (r - l);
        float ty = -(t + b) / (t - b);
        float tz = -(f + n) / (f - n);

        m00 = 2f / (r - l);
        m11 = 2f / (t - b);
        m22 = -2f / (f - n);
        m03 = tx;
        m13 = ty;
        m23 = tz;

        return this;
    }

//    public Mat4 add(Mat4 o){
//        for(int x = 0;x < 4;x++){
//            for(int y = 0;y < 4;y++) m[x][y] += o.m[x][y];
//        }
//        return this;
//    }
//
//    public Mat4 scl(float v){
//        for(int x = 0;x < 4;x++){
//            for(int y = 0;y < 4;y++) m[x][y] *= v;
//        }
//        return this;
//    }
//
//    public Mat4 transpose(){
//        float[][] r = new float[4][4];
//        for(int x = 0;x < 4;x++){
//            for(int y = 0;y < 4;y++) r[x][y] = m[y][x];
//        }
//        m = r;
//        return this;
//    }


    public FloatBuffer buffer(){
        MemoryStack stack = MemoryStack.stackPush();
        FloatBuffer buffer = stack.mallocFloat(4 * 4);
        buffer.put(m00).put(m10).put(m20).put(m30);
        buffer.put(m01).put(m11).put(m21).put(m31);
        buffer.put(m02).put(m12).put(m22).put(m32);
        buffer.put(m03).put(m13).put(m23).put(m33);
        buffer.flip();
        return buffer;
    }

//    public static Mat4 frustum(float left, float right, float bottom, float top, float near, float far){
//        Mat4 frustum = new Mat4();
//
//        float a = (right + left) / (right - left);
//        float b = (top + bottom) / (top - bottom);
//        float c = -(far + near) / (far - near);
//        float d = -(2f * far * near) / (far - near);
//
//        frustum.m[0][0] = (2f * near) / (right - left);
//        frustum.m[1][1] = (2f * near) / (top - bottom);
//        frustum.m[0][2] = a;
//        frustum.m[1][2] = b;
//        frustum.m[2][2] = c;
//        frustum.m[3][2] = -1f;
//        frustum.m[2][3] = d;
//        frustum.m[3][3] = 0f;
//
//        return frustum;
//    }
//
//    public static Mat4 perspective(float fovy, float aspect, float near, float far){
//        Mat4 perspective = new Mat4();
//
//        float f = 1 / tan(fovy / 2);
//
//        perspective.m[0][0] = f / aspect;
//        perspective.m[1][1] = f;
//        perspective.m[2][2] = (far + near) / (near - far);
//        perspective.m[3][2] = -1f;
//        perspective.m[2][3] = (2f * far * near) / (near - far);
//        perspective.m[3][3] = 0f;
//
//        return perspective;
//    }
//
//    public static Mat4 translate(float x, float y, float z){
//        Mat4 translation = new Mat4();
//
//        translation.m[0][3] = x;
//        translation.m[1][3] = y;
//        translation.m[2][3] = z;
//
//        return translation;
//    }
//
//    public static Mat4 rotate(float angle, float x, float y, float z){
//        Mat4 rotation = new Mat4();
//
//        float c = cos(angle);
//        float s = sin(angle);
//        Vec3 vec = new Vec3(x, y, z);
//        if(vec.len() != 1f){
//            vec = vec.nor();
//            x = vec.x;
//            y = vec.y;
//            z = vec.z;
//        }
//
//        rotation.m[0][0] = x * x * (1f - c) + c;
//        rotation.m[1][0] = y * x * (1f - c) + z * s;
//        rotation.m[2][0] = x * z * (1f - c) - y * s;
//        rotation.m[0][1] = x * y * (1f - c) - z * s;
//        rotation.m[1][1] = y * y * (1f - c) + c;
//        rotation.m[2][1] = y * z * (1f - c) + x * s;
//        rotation.m[0][2] = x * z * (1f - c) + y * s;
//        rotation.m[1][2] = y * z * (1f - c) - x * s;
//        rotation.m[2][2] = z * z * (1f - c) + c;
//
//        return rotation;
//    }
//
//    public static Mat4 scale(float x, float y, float z){
//        Mat4 scaling = new Mat4();
//
//        scaling.m[0][0] = x;
//        scaling.m[1][1] = y;
//        scaling.m[2][2] = z;
//
//        return scaling;
//    }
}
