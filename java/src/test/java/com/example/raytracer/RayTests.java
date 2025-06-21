package com.example.raytracer;

import com.example.raytracer.vector.SpatialVec;
import com.example.raytracer.vector.Vec3;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class RayTests{
    @Test
    public void testAt() {
        SpatialVec origin = new SpatialVec(1,2,3);
        SpatialVec direction = new SpatialVec(3,2,1);
        Ray r = new Ray(origin, direction);
        assertThat(Vec3.distance(origin, r.at(0)), closeTo(0, 1e-12));
        assertThat(Vec3.distance(origin.add(direction), r.at(1)), closeTo(0, 1e-12));
    }
}
