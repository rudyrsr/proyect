package org.am.core.web.service.admingeneral;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface  FileService {
    default void save(List<MultipartFile> files, Integer codeStudent, String fechaNacimiento) {
    }
}
