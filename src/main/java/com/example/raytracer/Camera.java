package com.example.raytracer;

import com.example.raytracer.vector.SpatialVec;

public class Camera {
    public static class Builder {
        private double _viewportHeight = 2.0;
        private double _focalLength = 1.0;
        private double _aspectRatio = 16.0 / 9.0;

        private Builder() {
        }

        public Builder aspectRatio(double ratio) {
            _aspectRatio = ratio;
            return this;
        }

        public Builder viewportHeight(double height) {
            _viewportHeight = height;
            return this;
        }

        public Builder focalLength(double length) {
            _focalLength = length;
            return this;
        }

        Camera build() {
            return new Camera(this);
        }
    }

    private final SpatialVec _lowerLeftCorner;
    private final SpatialVec _horizontal;
    private final SpatialVec _vertical;

    private Camera(Builder b) {
        double viewportWidth = b._aspectRatio * b._viewportHeight;
        _horizontal = new SpatialVec(viewportWidth, 0.0, 0.0);
        _vertical = new SpatialVec(0.0, b._viewportHeight, 0.0);
        _lowerLeftCorner = SpatialVec.origin()
                .minus(_horizontal.divide(2))
                .minus(_vertical.divide(2))
                .minus(new SpatialVec(0, 0, b._focalLength));
    }

    public Ray getRay(double u, double v) {
        SpatialVec direction = _lowerLeftCorner
                .add(_horizontal.multiply(u))
                .add(_vertical.multiply(v));
        return new Ray(SpatialVec.origin(), direction);
    }

    public static Builder builder() {
        return new Builder();
    }

}
