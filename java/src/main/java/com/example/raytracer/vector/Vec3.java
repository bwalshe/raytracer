package com.example.raytracer.vector;

public abstract class Vec3<T extends Vec3<T>> {

    protected final double[] _vec;

    protected Vec3(double v1, double v2, double v3) {
        _vec = new double[]{v1, v2, v3};
    }

    public abstract T make(double v1, double v2, double v3);

    public T add(T other) {
        return make(
                _vec[0] + other._vec[0],
                _vec[1] + other._vec[1],
                _vec[2] + other._vec[2]
        );
    }

    public T minus(T other) {
        return add(other.multiply(-1));
    }

    public T multiply(double magnitude) {
        return make(
                _vec[0] * magnitude,
                _vec[1] * magnitude,
                _vec[2] * magnitude
        );
    }

    public T divide(double magnitude) {
        return make(
                _vec[0] / magnitude,
                _vec[1] / magnitude,
                _vec[2] / magnitude
        );
    }

    public T cross(T other) {
        return make(
                _vec[1] * other._vec[2] - _vec[2] * other._vec[1],
                _vec[2] * other._vec[0] - _vec[0] * other._vec[2],
                _vec[0] * other._vec[1] - _vec[1] * other._vec[0]
        );
    }

    public T unit() {
        return this.divide(this.length());
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return _vec[0] * _vec[0] + _vec[1] * _vec[1] + _vec[2] * _vec[2];
    }

    public T sqrt() {
        return make(Math.sqrt(_vec[0]), Math.sqrt(_vec[1]), Math.sqrt(_vec[2]));
    }
    public static <U extends Vec3<U>> double dot(U a, U b) {
        return a._vec[0] * b._vec[0]
                + a._vec[1] * b._vec[1]
                + a._vec[2] * b._vec[2];
    }

    public static <U extends Vec3<U>> double distance(U a, U b) {
        return a.add(b.multiply(-1)).length();
    }

}
