package it.unical.compracasa.controller.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class ImageUploadRest {

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    @CrossOrigin(origins = "http://localhost:4200")
    public String uploadImage(@RequestPart("image") MultipartFile file, @RequestParam("filename") String filename) throws IOException {
        System.out.println("Chiamata alla uploadRest");

        // Ensure the uploads directory exists
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(filename);

        int counter = 1;
        String savedName = filename;
        while (Files.exists(filePath)) {
            int lastDotIndex = filename.lastIndexOf('.');
            String baseName = (lastDotIndex == -1) ? filename : filename.substring(0, lastDotIndex);
            String extension = (lastDotIndex == -1) ? "" : filename.substring(lastDotIndex);
            savedName = baseName + "_" + counter + extension;
            filePath = uploadPath.resolve(savedName);
            ++counter;
        }

        Files.write(filePath, file.getBytes());
        System.out.println(filePath);
        return savedName;
    }
}
