package it.unical.compracasa.controller.rest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class ImageRest {

    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping("/image")
    public ResponseEntity<Resource> getImage(@RequestParam String filename) throws Exception {
        System.out.println("Chiamate ImageRest");
        Path imagePath = Paths.get(UPLOAD_DIR, filename);

        if (!Files.exists(imagePath)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        System.out.println("2 ImageRest");
        Resource resource;
        try {
            resource = new UrlResource(imagePath.toUri());
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("3 ImageRest");
        // Determine the content type
        String contentType = Files.probeContentType(imagePath);

        // Fallback to binary stream if type is null
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // Return the image with appropriate headers
        System.out.println("Fine ImageRest");
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
            .body(resource);
    }



    private Object shit(String filename) throws Exception {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new Exception("File not found: " + filename);
        }

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }
}

