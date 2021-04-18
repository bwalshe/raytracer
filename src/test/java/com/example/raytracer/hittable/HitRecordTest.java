package com.example.raytracer.hittable;

import com.example.raytracer.Ray;
import com.example.raytracer.vector.SpatialVec;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class HitRecordTest {
    @Test
    public void testFrontFace() {
        Ray r = new Ray(SpatialVec.origin(), new SpatialVec(1,1,1));
        HitRecord insideHit = new HitRecord(r,r.getDirection(),r.getDirection(),1);
        assertThat(insideHit.frontFace, equalTo(false));
        assertThat(insideHit.normal.x(), closeTo(-1.0, 1e-12));

        HitRecord outsideHit = new HitRecord(r,r.getDirection(), r.getDirection().multiply(-1), 1);
        assertThat(outsideHit.frontFace, equalTo(true));
    }
}
