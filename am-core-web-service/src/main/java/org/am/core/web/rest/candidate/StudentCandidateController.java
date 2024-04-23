package org.am.core.web.rest.candidate;


import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.candidate.StudentCandidateDto;
import org.am.core.web.dto.candidate.StudentCandidateRequest;
import org.am.core.web.service.admingeneral.candidate.StudentCandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@RestController
@RequestMapping("/admissions/candidates")
@RequiredArgsConstructor
public class StudentCandidateController {

    private final StudentCandidateService studentCandidateService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentCandidateDto> getCandidateById(@PathVariable final String id) {
        return ResponseEntity.ok(studentCandidateService.getCandidateById(id).orElseThrow(() -> new IllegalArgumentException("Candidate with " + id + "not exist")));
    }

    @PostMapping
    public ResponseEntity<StudentCandidateDto> create(@RequestBody final StudentCandidateRequest studentCandidateRequest
                                                      ) throws URISyntaxException, IOException {



        StudentCandidateDto studentCandidateDto = studentCandidateService.save(studentCandidateRequest);

        return ResponseEntity
                .created(new URI("/admissions/candidates/" + studentCandidateDto.id()))
                .body(studentCandidateDto);
    }

    @PostMapping("/profile/{estudianteId}")
    public ResponseEntity<StudentCandidateDto> profile(@PathVariable final String estudianteId, @RequestParam MultipartFile profile) throws URISyntaxException {
        if (profile.isEmpty()) {
            throw new IllegalArgumentException("No se subio ninguna foto");
        }

        String fileExtension = profile.getOriginalFilename().toLowerCase();
        if (!(fileExtension.endsWith(".png") || fileExtension.endsWith(".jpg") || fileExtension.endsWith(".jpeg"))) {
            throw new IllegalArgumentException("Tipo de archivo no permitido. Solo se permiten archivos PNG, JPG y JPEG.");
        }

        StudentCandidateDto studentCandidateDto =  studentCandidateService.uploadProfile(profile, estudianteId);
        return ResponseEntity
                .created(new URI("/admissions/candidates/profile/" + estudianteId + studentCandidateDto.id()))
                .body(studentCandidateDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentCandidateDto> editCareer(@RequestBody final StudentCandidateDto dto, @PathVariable final String  id) {
        if (dto.id() == null) {
            throw new IllegalArgumentException("Invalid candidate ID");
        }

        if (!Objects.equals(id, dto.id())) {
            throw new IllegalArgumentException("Invalid ID");
        }

        return ResponseEntity
                .ok()
                .body(studentCandidateService.edit(dto));
    }


    @DeleteMapping("/{candidateId}")
    public ResponseEntity<Void> delete(@PathVariable final String candidateId) {
        studentCandidateService.delete(candidateId);
        return ResponseEntity.noContent().build();
    }
}
