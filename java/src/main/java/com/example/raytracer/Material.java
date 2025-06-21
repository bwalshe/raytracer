package com.example.raytracer;

import com.example.raytracer.hittable.HitRecord;
import com.example.raytracer.vector.RgbVec;

import java.util.Optional;

@FunctionalInterface
public interface Material {
    Optional<Ray> scatter(Ray r, HitRecord rec, RgbVec attenuation);
}
