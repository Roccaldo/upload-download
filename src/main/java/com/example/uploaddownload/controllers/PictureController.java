package com.example.uploaddownload.controllers;

import com.example.uploaddownload.services.PictureService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        return pictureService.upload(file);
    }

    @GetMapping("/download")
    public byte[] download(@RequestParam String file, HttpServletResponse response) throws IOException {
        System.out.println("downloading " + file);
        return pictureService.download(file, response);
    }
}
