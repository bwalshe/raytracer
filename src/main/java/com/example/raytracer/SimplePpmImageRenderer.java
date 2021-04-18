package com.example.raytracer;

import com.example.raytracer.vector.RgbVec;
import com.example.raytracer.vector.SpatialVec;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class SimplePpmImageRenderer {

    static boolean hitSphere(SpatialVec center, double radius, Ray r) {
        SpatialVec oc = r.getOrigin().minus(center);
        double a = SpatialVec.dot(r.getDirection(), r.getDirection());
        double b = 2.0 * SpatialVec.dot(oc, r.getDirection());
        double c = SpatialVec.dot(oc, oc) - radius * radius;
        double discriminant = b * b - 4 * a * c;
        return (discriminant > 0);
    }

    static RgbVec rayColor(Ray r) {
        if (hitSphere(new SpatialVec(0, 0, -1), 0.5, r)) {
            return new RgbVec(1.0, 0.0, 0.0);
        }
        SpatialVec unitDirection = r.getDirection().unit();
        double t = 0.5 * (unitDirection.y() + 1.0);
        return new RgbVec(1.0, 1.0, 1.0)
                .multiply(1.0 - t)
                .add(new RgbVec(0.5, 0.7, 1.0)
                        .multiply(t));
    }

    public static void main(String[] args) throws IOException {
        double aspectRatio = 16.0 / 9.0;
        int imageWidth = 400;
        int imageHeight = (int) (imageWidth / aspectRatio);

        double viewport_height = 2.0;
        double viewportWidth = aspectRatio * viewport_height;
        double focalLength = 1.0;

        SpatialVec origin = SpatialVec.origin();
        SpatialVec horizontal = new SpatialVec(viewportWidth, 0, 0);
        SpatialVec vertical = new SpatialVec(0, viewport_height, 0);
        SpatialVec lowerLeftCorner = origin.minus(horizontal.divide(2))
                .minus(vertical.divide(2))
                .minus(new SpatialVec(0, 0, focalLength));

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("image.ppm"), StandardOpenOption.CREATE)) {
            writer.write(String.format("P3\n%d %d\n255\n", imageWidth, imageHeight));
            for (int j = imageHeight - 1; j >= 0; --j) {
                System.out.printf("Scanlines remaining: %d\n", j);
                for (int i = 0; i < imageWidth; ++i) {
                    double u = ((double) i) / (imageWidth - 1);
                    double v = ((double) j) / (imageHeight - 1);
                    SpatialVec direction = lowerLeftCorner
                            .add(horizontal.multiply(u))
                            .add(vertical.multiply(v))
                            .minus(origin);
                    Ray r = new Ray(origin, direction);
                    RgbVec pixelColor = rayColor(r);
                    writer.write(pixelColor + "\n");
                }
            }
        }
        System.out.println("Done.");
    }
}