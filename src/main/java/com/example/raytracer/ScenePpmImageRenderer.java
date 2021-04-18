package com.example.raytracer;

import com.example.raytracer.hittable.Hittable;
import com.example.raytracer.hittable.HittableList;
import com.example.raytracer.hittable.Sphere;
import com.example.raytracer.vector.RgbVec;
import com.example.raytracer.vector.SpatialVec;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ScenePpmImageRenderer {

    static RgbVec rayColor(Ray r, List<Hittable> world) {
        return HittableList.closestHit(world, r, 0, 100)
                .map(hit -> new RgbVec(
                        hit.normal.x() + 1,
                        hit.normal.y() + 1,
                        hit.normal.z() + 1)
                        .multiply(0.5))
                .orElseGet(() -> {
                    SpatialVec unitDirection = r.getDirection().unit();
                    double t = 0.5 * (unitDirection.y() + 1.0);
                    return new RgbVec(1.0, 1.0, 1.0)
                            .multiply(1.0 - t)
                            .add(new RgbVec(0.5, 0.7, 1.0)
                                    .multiply(t));
                });
    }

    public static void main(String[] args) throws IOException {
        Logger logger = LogManager.getLogger(ScenePpmImageRenderer.class);
        double aspectRatio = 16.0 / 9.0;
        int imageWidth = 800;
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

        List<Hittable> world = List.of(
                new Sphere(new SpatialVec(0, 0, -1), 0.5),
                new Sphere(new SpatialVec(0,-100.5,-1), 100)
        );

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("image.ppm"), StandardOpenOption.CREATE)) {
            writer.write(String.format("P3\n%d %d\n255\n", imageWidth, imageHeight));
            for (int j = imageHeight - 1; j >= 0; --j) {
                logger.info("Scanlines remaining: " + j);
                for (int i = 0; i < imageWidth; ++i) {
                    double u = ((double) i) / (imageWidth - 1);
                    double v = ((double) j) / (imageHeight - 1);
                    SpatialVec direction = lowerLeftCorner
                            .add(horizontal.multiply(u))
                            .add(vertical.multiply(v))
                            .minus(origin);
                    Ray r = new Ray(origin, direction);
                    RgbVec pixelColor = rayColor(r, world);
                    writer.write(pixelColor + "\n");
                }
            }
        }
        logger.info("Done.");
    }
}