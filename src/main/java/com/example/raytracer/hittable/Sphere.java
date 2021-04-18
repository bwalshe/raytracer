package com.example.raytracer.hittable;

import com.example.raytracer.Ray;
import com.example.raytracer.vector.SpatialVec;

import java.util.Optional;

public class Sphere implements Hittable {
    private final SpatialVec _center;
    private final double _radius;

    public Sphere(SpatialVec center, double radius) {
        _center = center;
        _radius = radius;
    }

    public Optional<HitRecord> hit(Ray r, double tMin, double tMax) {
        SpatialVec oc = r.getOrigin().minus(_center);
        double a = r.getDirection().lengthSquared();
        double halfB = SpatialVec.dot(oc, r.getDirection());
        double c = oc.lengthSquared() - _radius * _radius;

        double discriminant = halfB * halfB - a * c;
        if (discriminant < 0)
            return Optional.empty();

        double sqrtd = Math.sqrt(discriminant);

        // Find the nearest root that lies in the acceptable range.
        double root = (-halfB - sqrtd) / a;
        if (root < tMin || tMax < root) {
            root = (-halfB + sqrtd) / a;
            if (root < tMin || tMax < root)
                return Optional.empty();
        }

        SpatialVec p = r.at(root);
        SpatialVec n = p.minus(_center).divide(_radius);
        return Optional.of(new HitRecord(r, p, n, root));
    }
}
