package com.example.uploaddownload.services;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class PictureService {
    @Value("${folderLocation}")
    private String folderLocation;

    /**
     * method to upload a picture
     * the file to upload
     *
     * @param fileToUpload the file to upload
     * @return the name of the file uploaded
     * @throws IOException to check some error during I/O operation
     */
    public String upload(@RequestParam MultipartFile fileToUpload) throws IOException {
        //get the extension of the file
        String extension = FilenameUtils.getExtension(fileToUpload.getOriginalFilename());

        //generate a random name for the file
        String randomName = UUID.randomUUID().toString();

        //the complete name of the file
        String fileName = randomName + "." + extension;

        //the path for the destination of the file into the folder
        File destinationfolder = new File(folderLocation + "//" + fileName);

        //create the folder
        File folder = new File(folderLocation);
        //create the folder if it doesn't exist
        folder.mkdirs();

        //transfer the file to the folder and return the name of it
        fileToUpload.transferTo(destinationfolder);
        return fileName + " --> Successfully uploaded";
    }

    public byte[] download(String fileToDownload, HttpServletResponse response) throws IOException {
        //the path for the destination of the file into the folder
        File destinationfolder = new File(folderLocation + "//" + fileToDownload);
        //check if the folder doesn't exist it throw exception
        if (!destinationfolder.exists()) {
            throw new IOException("File not found");
        }

        //get the extension of the file
        String extension = FilenameUtils.getExtension(fileToDownload);

        //check what type of extension is the file
        switch (extension) {
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpeg":
            case "jpg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileToDownload + "\"");
        return IOUtils.toByteArray(new FileInputStream(destinationfolder));
    }
}
