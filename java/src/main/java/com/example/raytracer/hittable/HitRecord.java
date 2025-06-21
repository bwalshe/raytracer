package com.example.raytracer.hittable;

import com.example.raytracer.Ray;
import com.example.raytracer.vector.SpatialVec;

public class HitRecord {
    public final SpatialVec point;
    public final SpatialVec normal;
    public final double t;
    public final boolean frontFace;

    public HitRecord(Ray r, SpatialVec point, SpatialVec normal, double t) {
        this.point = point;
        this.t = t;
        this.frontFace = SpatialVec.dot(r.getDirection(), normal) < 0;
        this.normal = this.frontFace ? normal : normal.multiply(-1);
    }
}
