package com.example.raytracer.vector;

import java.util.Random;

public final class SpatialVec extends Vec3<SpatialVec> {

    private static final SpatialVec ORIGIN = new SpatialVec(0, 0, 0);
    private static final Random randomGen = new Random();


    public SpatialVec(double x, double y, double z) {
        super(x, y, z);
    }

    @Override
    public SpatialVec make(double x, double y, double z) {
        return new SpatialVec(x, y, z);
    }

    public double x() {
        return _vec[0];
    }

    public double y() {
        return _vec[1];
    }

    public double z() {
        return _vec[2];
    }

    public static SpatialVec origin() {
        return ORIGIN;
    }

    public static SpatialVec random(double min, double max) {
        if(min > max) throw new IllegalArgumentException("min must be less than max");
        double scale  = max - min;
        return new SpatialVec(randomGen.nextDouble() * scale + min,
                randomGen.nextDouble() * scale + min,
                randomGen.nextDouble() * scale + min);
    }

    public static SpatialVec random() {
        return random(-1, 1);
    }

    public static SpatialVec randomInUnitSphere() {
        while (true) {
            SpatialVec v = random();
            if(v.lengthSquared() < 1)
                return v;
        }
    }
}
