package com.example.raytracer;

import com.example.raytracer.vector.RgbVec;
import com.example.raytracer.vector.SpatialVec;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class SimplePpmImageRenderer {

    static double hitSphere(SpatialVec center, double radius, Ray r) {
        SpatialVec oc = r.getOrigin().minus(center);
        double a = r.getDirection().lengthSquared();
        double halfB = SpatialVec.dot(oc, r.getDirection());
        double c = oc.lengthSquared() - radius * radius;
        double discriminant = halfB * halfB - a * c;
        if(discriminant <= 0) {
            return 0.0;
        } else {
            return (-halfB - Math.sqrt(discriminant)) / a;
        }
    }

    static RgbVec rayColor(Ray r) {
        SpatialVec sphereCenter = new SpatialVec(0, 0,-1);
        double t = hitSphere(sphereCenter, 0.5, r);
        if (t > 0.0) {
            SpatialVec surfaceNormal = r.at(t).minus(sphereCenter);
            return new RgbVec(surfaceNormal.x()+1, surfaceNormal.y()+1, surfaceNormal.z()+1).multiply(0.5);
        }
        SpatialVec unitDirection = r.getDirection().unit();
        t = 0.5 * (unitDirection.y() + 1.0);
        return new RgbVec(1.0, 1.0, 1.0)
                .multiply(1.0 - t)
                .add(new RgbVec(0.5, 0.7, 1.0)
                        .multiply(t));
    }

    public static void main(String[] args) throws IOException {
        double aspectRatio = 16.0 / 9.0;
        int imageWidth = 400;
        int imageHeight = (int) (imageWidth / aspectRatio);

        double viewportHeight = 2.0;
        double viewportWidth = aspectRatio * viewportHeight;
        double focalLength = 1.0;

        SpatialVec origin = SpatialVec.origin();
        SpatialVec horizontal = new SpatialVec(viewportWidth, 0, 0);
        SpatialVec vertical = new SpatialVec(0, viewportHeight, 0);
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