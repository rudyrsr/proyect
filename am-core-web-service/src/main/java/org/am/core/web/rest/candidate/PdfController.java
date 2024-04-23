package org.am.core.web.rest.candidate;

import lombok.RequiredArgsConstructor;
import org.am.core.web.service.admingeneral.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class PdfController {

    public final FileService fileService;

    @PostMapping("/uploadPdf/{codeStudent}/{fechaNacimiento}")
    public ResponseEntity<String> uploadPdf(@RequestParam("files") List<MultipartFile> files,
                                            @PathVariable final Integer codeStudent,
                                            @PathVariable final String fechaNacimiento) throws IOException {
        try {
            fileService.save(files,codeStudent, fechaNacimiento);
            return ResponseEntity.ok("Archivos PDF subido correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

}