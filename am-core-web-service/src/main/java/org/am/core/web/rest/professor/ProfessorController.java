package org.am.core.web.rest.professor;

import  lombok.RequiredArgsConstructor;
import org.am.core.web.dto.professor.ProfessorDto;
import org.am.core.web.dto.professor.ProfessorRequest;
import org.am.core.web.service.professor.ProfessorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/professors")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @GetMapping()
    public ResponseEntity<List<ProfessorDto>> findAll(@RequestParam final Integer areaId){
        return ResponseEntity.ok().body(professorService.listProfessorByArea(areaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDto> getById(@PathVariable final Integer id){
        return ResponseEntity.ok().body(
                professorService.getById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Not found Professor for id: " + id))
        );
    }

    @PostMapping
    public ResponseEntity<ProfessorDto> create(@RequestBody final ProfessorRequest requestBody) throws URISyntaxException {
        ProfessorDto professorDto = professorService.save(requestBody);

        return ResponseEntity
                .created(new URI("/professors/" + professorDto.id()))
                .body(professorDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDto> edit(@PathVariable final Integer id,
                                             @RequestBody final ProfessorDto professorDto){
        if (professorDto.id()==null){
            throw new IllegalArgumentException("Invalid professor id, null value");
        }
        if(!Objects.equals(professorDto.id(),id)){
            throw new IllegalArgumentException("Invalid id");
        }
        return ResponseEntity.ok()
                .body(professorService.edit(professorDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id,
                                       @RequestParam final Integer areaId) {
        try {
            professorService.delete(areaId, id);
        } catch (DataIntegrityViolationException e) {
            professorService.logicalDelete(areaId, id);
        }
        return ResponseEntity.ok().build();
    }
}
