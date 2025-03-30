package jbsae.graphics.draw;

import jbsae.graphics.*;
import jbsae.struct.*;
import jbsae.struct.prim.*;

public class SortedDraw extends Draw{
    public Seq<DrawRequest> requests = new Seq<>();

    public float z = 0f;


    @Override
    public void draw(Region region, float x, float y, float w, float h){
        RectDrawRequest request = (RectDrawRequest)setup(region, new RectDrawRequest());
        request.x = x;
        request.y = y;
        request.w = w;
        request.h = h;
        requests.add(request);
    }

    @Override
    public void draw(Region region, float x, float y, float w, float h, float r){
        RotatedRectDrawRequest request = (RotatedRectDrawRequest)setup(region, new RotatedRectDrawRequest());
        request.x = x;
        request.y = y;
        request.w = w;
        request.h = h;
        request.r = r;
        requests.add(request);
    }

    @Override
    public void draw(Region region, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4){
        DrawQuadRequest request = (DrawQuadRequest)setup(region, new DrawQuadRequest());
        request.x1 = x1;
        request.y1 = y1;
        request.x2 = x2;
        request.y2 = y2;
        request.x3 = x3;
        request.y3 = y3;
        request.x4 = x4;
        request.y4 = y4;
        requests.add(request);
    }


    public void render(){
        FloatMap<Layer> layers = new FloatMap<>();

        for(DrawRequest request : requests) if(!layers.contains(request.z)) layers.add(request.z, new Layer(request.z));

        layers.eachKey(k -> layers.get(k).create(requests.size / layers.size));

        for(DrawRequest request : requests) layers.get(request.z).requests.add(request);

        Seq<Layer> sorted = new Seq<>(layers.values());
        sorted.sort(l -> l.z);

        for(Layer layer : sorted){
            for(DrawRequest request : layer.requests){
                fill.rgba8888(request.rgba8888);
                if(request instanceof RotatedRectDrawRequest r) super.draw(r.region, r.x, r.y, r.w, r.h, r.r);
                else if(request instanceof RectDrawRequest r) super.draw(r.region, r.x, r.y, r.w, r.h);
                else if(request instanceof DrawQuadRequest r) super.draw(r.region, r.x1, r.y1, r.x2, r.y2, r.x3, r.y3, r.x4, r.y4);
            }
        }

        requests.clear();
    }


    public DrawRequest setup(Region region, DrawRequest request){
        request.region = region;
        request.rgba8888 = fill.rgba8888();
        request.z = z;
        return request;
    }


    public class DrawRequest{
        public Region region;
        public int rgba8888;
        public float z;
    }

    public class RectDrawRequest extends DrawRequest{
        public float x, y, w, h;
    }

    public class RotatedRectDrawRequest extends RectDrawRequest{
        public float r;
    }

    public class DrawQuadRequest extends DrawRequest{
        public float x1, y1, x2, y2, x3, y3, x4, y4;
    }


    public class Layer{
        public float z;
        public Seq<DrawRequest> requests;

        public Layer(float z){
            this.z = z;
        }

        public void create(int size){
            requests = new Seq<>(size);
        }
    }
}
