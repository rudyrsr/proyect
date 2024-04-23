package org.am.core.web.service.admingeneral.candidate;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.document.candidate.StudentCandidate;
import org.am.core.web.dto.candidate.StudentCandidateDto;
import org.am.core.web.dto.candidate.StudentCandidateRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.candidate.StudentCandidateRepository;
import org.am.core.web.util.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class StudentCandidateService implements CustomMap<StudentCandidateDto, StudentCandidate> {

    private final StudentCandidateRepository studentCandidateRepository;

    @Value("${file.path}")
    private String profilePath;

    @Transactional(readOnly = true)
    public Optional<StudentCandidateDto> getCandidateById(String id) {
        return studentCandidateRepository.findById(id).map(this::toDto);
    }

    public StudentCandidateDto save(StudentCandidateRequest studentCandidateRequest) {
        StudentCandidate studentCandidateSave = studentCandidateRepository.save(this.toEntity(studentCandidateRequest));
        return toDto(studentCandidateSave);
    }


    public StudentCandidateDto uploadProfile(MultipartFile profile, String estudianteId){
        StudentCandidate studentCandidate = studentCandidateRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + estudianteId));

        String originalFilename = profile.getOriginalFilename();
        assert originalFilename != null;
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String profileFileName = studentCandidate.getPathProfile() + fileExtension;

        String dir = System.getProperty("user.dir") + "/" + profilePath;
        try {
            profile.transferTo(new File(dir + "/" + profileFileName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        studentCandidate.setState("paso 2");
        return toDto(studentCandidateRepository.save(studentCandidate));
    }






    public StudentCandidateDto edit(StudentCandidateDto studentCandidateDto) {
        StudentCandidate candidateFromDB = studentCandidateRepository.findById(studentCandidateDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        candidateFromDB.setIdNumber(studentCandidateDto.idNumber());
        candidateFromDB.setIssuedIn(studentCandidateDto.issuedIn());
        candidateFromDB.setDateOfBirth(studentCandidateDto.dateOfBirth());
        candidateFromDB.setFirstNames(studentCandidateDto.firstNames());
        candidateFromDB.setPaternalLastName(studentCandidateDto.paternalLastName());
        candidateFromDB.setMaternalLastName(studentCandidateDto.maternalLastName());
        candidateFromDB.setNationality(studentCandidateDto.nationality());
        candidateFromDB.setGender(studentCandidateDto.gender());
        candidateFromDB.setCellPhoneNumber(studentCandidateDto.cellPhoneNumber());
        candidateFromDB.setEmail(studentCandidateDto.email());
        candidateFromDB.setCurrentAddress(studentCandidateDto.currentAddress());
        candidateFromDB.setCareerId(studentCandidateDto.careerId());

        return toDto(studentCandidateRepository.save(candidateFromDB));
    }

    public void delete(String id) {
        studentCandidateRepository.deleteById(id);
    }

    @Override
    public StudentCandidateDto toDto(StudentCandidate studentCandidate) {
        return new StudentCandidateDto(
                studentCandidate.getId(),
                studentCandidate.getIdNumber(),
                studentCandidate.getIssuedIn(),
                studentCandidate.getDateOfBirth(),
                studentCandidate.getFirstNames(),
                studentCandidate.getPaternalLastName(),
                studentCandidate.getMaternalLastName(),
                studentCandidate.getNationality(),
                studentCandidate.getGender(),
                studentCandidate.getCellPhoneNumber(),
                studentCandidate.getEmail(),
                studentCandidate.getCurrentAddress(),
                studentCandidate.getCode(),
                studentCandidate.getPathProfile(),
                studentCandidate.getPathCi(),
                studentCandidate.getPathDiploma(),
                studentCandidate.getCareerId(),
                studentCandidate.getPassword(),
                studentCandidate.getState()
        );
    }

    @Override
    public StudentCandidate toEntity(StudentCandidateDto studentCandidateDto) {
        return null;
    }

    public StudentCandidate toEntity(StudentCandidateRequest studentCandidateRequest) {

        String code = CommonUtils.generateRandomCode();

        Date currentDate = new Date();
        String dateSuffix = new SimpleDateFormat("yyyyMMdd").format(currentDate);

        String pathProfile = code + "_profile_" + dateSuffix;
        String pathCi = code + "_ci_" + dateSuffix;
        String pathDiploma = code + "_diploma_" + dateSuffix;

        StudentCandidate studentCandidate = new StudentCandidate();
        studentCandidate.setIdNumber(studentCandidateRequest.idNumber());
        studentCandidate.setIssuedIn(studentCandidateRequest.issuedIn());
        studentCandidate.setDateOfBirth(studentCandidateRequest.dateOfBirth());
        studentCandidate.setFirstNames(studentCandidateRequest.firstNames());
        studentCandidate.setPaternalLastName(studentCandidateRequest.paternalLastName());
        studentCandidate.setMaternalLastName(studentCandidateRequest.maternalLastName());
        studentCandidate.setNationality(studentCandidateRequest.nationality());
        studentCandidate.setGender(studentCandidateRequest.gender());
        studentCandidate.setCellPhoneNumber(studentCandidateRequest.cellPhoneNumber());
        studentCandidate.setEmail(studentCandidateRequest.email());
        studentCandidate.setCurrentAddress(studentCandidateRequest.currentAddress());
        studentCandidate.setCode(code);
        studentCandidate.setPathProfile(pathProfile);
        studentCandidate.setPathCi(pathCi);
        studentCandidate.setPathDiploma(pathDiploma);
        studentCandidate.setCareerId(studentCandidateRequest.careerId());
        studentCandidate.setPassword(studentCandidateRequest.password());
        studentCandidate.setState("paso 1");

        return studentCandidate;
    }
}
