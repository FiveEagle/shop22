package com.example.pmproject.Service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class imgService {
    public String uploadFile(String uploadPath, String originalFileName, byte[] filedata) throws IOException {
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String saveFileName = uuid + extension;
        String uploadFullUrl = uploadPath + saveFileName;

        FileOutputStream fos = new FileOutputStream(uploadFullUrl);
        fos.write(filedata);
        fos.close();

        return saveFileName;
    }

    public void deleteFile(String uploadPath, String fileName) {
        String deleteFileName = uploadPath + fileName;

        File deleteFile = new File(deleteFileName);
        if(deleteFile.exists()) {
            deleteFile.delete();
        }
    }
}
