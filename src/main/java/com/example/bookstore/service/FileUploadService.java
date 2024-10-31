package com.example.bookstore.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadService {

    private final String uploadDir = "/Users/aungpaingtun/Desktop/upload ";

    public String uploadFile(MultipartFile file) throws IOException{
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }
}
