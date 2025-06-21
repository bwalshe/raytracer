package com.example.raytracer.hittable;

import com.example.raytracer.Ray;

import java.util.List;
import java.util.Optional;

public final class HittableList {
    private HittableList(){}

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static Optional<HitRecord> pickCloser(Optional<HitRecord> a, Optional<HitRecord> b) {
        if (a.isEmpty())
            return b;
        if (b.isEmpty())
            return a;
        return a.get().t <= b.get().t ? a : b;
    }

    public static Optional<HitRecord> closestHit(List<Hittable> hittables, Ray r, double tMin, double tMax) {
        return hittables.stream()
                .map(h -> h.hit(r, tMin, tMax))
                .reduce(Optional.empty(), HittableList::pickCloser);
    }
}
