package com.example.raytracer;

import com.example.raytracer.vector.SpatialVec;


public class Ray {
    private final SpatialVec _origin;
    private final SpatialVec _direction;

    public Ray(SpatialVec origin, SpatialVec direction) {
        _origin = origin;
        _direction = direction;
    }

    public SpatialVec getOrigin() {
        return _origin;
    }

    public SpatialVec getDirection() {
        return _direction;
    }

    public SpatialVec at(double t) {
        return _origin.add(_direction.multiply(t));
    }
}
