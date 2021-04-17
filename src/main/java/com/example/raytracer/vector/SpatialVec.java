package com.example.raytracer.vector;

public final class SpatialVec extends Vec3<SpatialVec> {

    private static final SpatialVec ORIGIN = new SpatialVec(0, 0, 0);

    public SpatialVec(double x, double y, double z) {
        super(x, y, z);
    }

    public static SpatialVec origin() {
        return ORIGIN;
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
}
