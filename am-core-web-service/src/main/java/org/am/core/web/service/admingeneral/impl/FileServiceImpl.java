package org.am.core.web.service.admingeneral.impl;

import org.am.core.web.service.admingeneral.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl  implements FileService {

    @Value("${file.path.pdf}")
    private String filePath;

    @Override
    public void save(List<MultipartFile> files, Integer codeStudent, String fechaNacimiento) {
        String dir = System.getProperty("user.dir") + "/" + filePath;
        File directory = new File(dir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        for (MultipartFile file : files) {
            if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
                throw new IllegalArgumentException("Solo se permiten archivos PDF.");
            }

            String newFilename = codeStudent + "_ci_" + fechaNacimiento + "_" + file.getOriginalFilename();

            try {
                file.transferTo(new File(dir + newFilename));
            } catch (IOException e) {
                System.out.println("Error al guardar el archivo: " + e.getMessage());
            }
        }
    }
}