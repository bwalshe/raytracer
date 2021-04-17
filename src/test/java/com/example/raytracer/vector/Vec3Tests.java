package com.example.raytracer.vector;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

public class Vec3Tests {

    @Test
    public void testSpatialVec() {
        SpatialVec v = new SpatialVec(1,2,3);
        assertThat(v.x(), closeTo(1.0, 1e-12));
        assertThat(v.y(), closeTo(2.0, 1e-12));
        assertThat(v.z(), closeTo(3.0, 1e-12));
    }

    @Test
    public void testRgbVec() {
        RgbVec rgb = new RgbVec(1,2,3);
        assertThat(rgb.r(), closeTo(1.0, 1e-12));
        assertThat(rgb.g(), closeTo(2.0, 1e-12));
        assertThat(rgb.b(), closeTo(3.0, 1e-12));
    }

    @Test
    public void testAdd() {
        RgbVec c = new RgbVec(1,2,3);
        RgbVec cc = c.add(c);
        assertThat(cc.r(), closeTo(2.0, 1e-12));
        assertThat(cc.g(), closeTo(4.0, 1e-12));
        assertThat(cc.b(), closeTo(6.0, 1e-12));
    }

    @Test
    public void testMultiply() {
        RgbVec c = new RgbVec(1,2,3);
        RgbVec cc = c.multiply(2);
        assertThat(cc.r(), closeTo(2.0, 1e-12));
        assertThat(cc.g(), closeTo(4.0, 1e-12));
        assertThat(cc.b(), closeTo(6.0, 1e-12));
    }

    @Test
    public void testDivide() {
        RgbVec c = new RgbVec(1,2,3);
        RgbVec cc = c.divide(0.5);
        assertThat(cc.r(), closeTo(2.0, 1e-12));
        assertThat(cc.g(), closeTo(4.0, 1e-12));
        assertThat(cc.b(), closeTo(6.0, 1e-12));
    }

    @Test
    public void testLengthSquared() {
        SpatialVec v = new SpatialVec(1,2,3);
        assertThat(v.lengthSquared(), closeTo(1 + 2*2 + 3*3, 1e-12));
    }

    @Test
    public void testLength() {
        SpatialVec v = new SpatialVec(1,2,3);
        assertThat(v.length(), closeTo(Math.sqrt(1 + 2*2 + 3*3), 1e-12));
    }

    @Test
    public void testDistance() {
        SpatialVec v = new SpatialVec(1,2,3);
        assertThat(SpatialVec.distance(SpatialVec.origin(), v), closeTo(v.length(), 1e-12));
        assertThat(SpatialVec.distance(v, v.multiply(-1)), closeTo(2 * v.length(), 1e-12) );
    }

    @Test
    public void testUnit()
    {
        SpatialVec v = new SpatialVec(1,2,3);
        assertThat(SpatialVec.distance(v.unit(), v.divide(v.length())), closeTo(0, 1e-12));
    }

    @Test
    public void testCross() {
        SpatialVec v1 = new SpatialVec(-5,2,-1);
        SpatialVec v2 = new SpatialVec(4, -4, 1);
        SpatialVec actual = v1.cross(v2);
        SpatialVec expected = new SpatialVec(-2, 1, 12);
        assertThat(Vec3.distance(actual, expected), closeTo(0.0, 1e-12));
    }

    @Test
    public void testDot() {
        SpatialVec v = new SpatialVec(1,2,3);
        assertThat(Vec3.dot(v,v), closeTo(v.lengthSquared(), 1e-12));
    }
}
