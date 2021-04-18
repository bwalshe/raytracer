package com.example.raytracer.hittable;

import com.example.raytracer.Ray;

import java.util.Optional;

public interface Hittable {
    Optional<HitRecord> hit(Ray r, double tMin, double tMax);
}
