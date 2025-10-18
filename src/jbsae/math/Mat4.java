package jbsae.math;

import org.lwjgl.system.*;

import java.nio.*;

import static jbsae.util.Mathf.*;


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

    public Mat4 add(Mat4 o){
        m00 += o.m00; m01 += o.m01; m02 += o.m02; m03 += o.m03;
        m10 += o.m10; m11 += o.m11; m12 += o.m12; m13 += o.m13;
        m20 += o.m20; m21 += o.m21; m22 += o.m22; m23 += o.m23;
        m30 += o.m30; m31 += o.m31; m32 += o.m32; m33 += o.m33;
        return this;
    }

    public Mat4 scl(float v){
        m00 *= v; m01 *= v; m02 *= v; m03 *= v;
        m10 *= v; m11 *= v; m12 *= v; m13 *= v;
        m20 *= v; m21 *= v; m22 *= v; m23 *= v;
        m30 *= v; m31 *= v; m32 *= v; m33 *= v;
        return this;
    }

    public Mat4 transpose(){
        float t;
        t = m01; m01 = m10; m10 = t;
        t = m02; m02 = m20; m20 = t;
        t = m03; m03 = m30; m30 = t;
        t = m12; m12 = m21; m21 = t;
        t = m13; m13 = m31; m31 = t;
        t = m23; m23 = m32; m32 = t;
        return this;
    }

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

    public static Mat4 frustum(float left, float right, float bottom, float top, float near, float far){
        Mat4 frustum = new Mat4();

        float a = (right + left) / (right - left);
        float b = (top + bottom) / (top - bottom);
        float c = -(far + near) / (far - near);
        float d = -(2f * far * near) / (far - near);

        frustum.m00 = (2f * near) / (right - left);
        frustum.m11 = (2f * near) / (top - bottom);
        frustum.m02 = a;
        frustum.m12 = b;
        frustum.m22 = c;
        frustum.m32 = -1f;
        frustum.m23 = d;
        frustum.m33 = 0f;

        return frustum;
    }

    public static Mat4 perspective(float fovy, float aspect, float near, float far){
        Mat4 perspective = new Mat4();

        float f = 1 / tan(fovy / 2);

        perspective.m00 = f / aspect;
        perspective.m11 = f;
        perspective.m22 = (far + near) / (near - far);
        perspective.m32 = -1f;
        perspective.m23 = (2f * far * near) / (near - far);
        perspective.m33 = 0f;

        return perspective;
    }

    public static Mat4 translate(float x, float y, float z){
        Mat4 translation = new Mat4();

        translation.m03 = x;
        translation.m13 = y;
        translation.m23 = z;

        return translation;
    }

    public static Mat4 rotate(float angle, float x, float y, float z){
        Mat4 rotation = new Mat4();

        float c = cos(angle);
        float s = sin(angle);
        Vec3 vec = new Vec3(x, y, z);
        if(vec.len() != 1f){
            vec = vec.nor();
            x = vec.x;
            y = vec.y;
            z = vec.z;
        }

        rotation.m00 = x * x * (1f - c) + c;
        rotation.m10 = y * x * (1f - c) + z * s;
        rotation.m20 = x * z * (1f - c) - y * s;
        rotation.m01 = x * y * (1f - c) - z * s;
        rotation.m11 = y * y * (1f - c) + c;
        rotation.m21 = y * z * (1f - c) + x * s;
        rotation.m02 = x * z * (1f - c) + y * s;
        rotation.m12 = y * z * (1f - c) - x * s;
        rotation.m22 = z * z * (1f - c) + c;

        return rotation;
    }

    public static Mat4 scale(float x, float y, float z){
        Mat4 scaling = new Mat4();

        scaling.m00 = x;
        scaling.m11 = y;
        scaling.m22 = z;

        return scaling;
    }
}
