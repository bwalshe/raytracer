package com.example.raytracer;

import com.example.raytracer.vector.RgbVec;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class SimplePpmImageRenderer {

    public static void main(String[] args) throws IOException {
        int imageWidth = 256;
        int imageHeight = 256;

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("image.ppm"), StandardOpenOption.CREATE)) {
            writer.write(String.format("P3\n%d %d\n255\n", imageWidth, imageHeight));
            for (int j = imageHeight - 1; j >= 0; --j) {
                System.out.printf("Scanlines remaining: %d\n", j);
                for (int i = 0; i < imageWidth; ++i) {
                    RgbVec color = new RgbVec(
                            ((double) i) / (imageWidth - 1),
                            ((double) j) / (imageHeight - 1),
                            0.25
                    );
                    writer.write(color + "\n");
                }
            }
        }
        System.out.println("Done.");
    }
}