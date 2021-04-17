package com.example.raytracer.vector;

public final class RgbVec extends Vec3<RgbVec> {

    private static final double SCALE = 255.9999;

    public RgbVec(double r, double g, double b) {
        super(r, g, b);
    }

    @Override
    public RgbVec make(double r, double g, double b) {
        return new RgbVec(r, g, b);
    }

    public double r() {
        return _vec[0];
    }

    public double g() {
        return _vec[1];
    }

    public double b() {
        return _vec[2];
    }

    @Override
    public String toString() {
        return String.format("%f %f %f", SCALE * r(), SCALE * g(), SCALE * b());
    }
}
