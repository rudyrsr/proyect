package org.am.core.web.rest.admingeneral;

import org.am.core.web.dto.admingeneral.ClassroomDto;
import org.am.core.web.dto.admingeneral.ClassroomRequest;
import org.am.core.web.service.admingeneral.ClassroomService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/areas/{areaId}/classrooms")
public class ClassroomController {
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public ResponseEntity<List<ClassroomDto>>listClassroomByArea(@PathVariable final Integer areaId){
        return ResponseEntity.ok().body(classroomService.getActiveClassroomByAreaId(areaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDto> getListClassroomByIdByArea(@PathVariable final Integer id){
        return ResponseEntity.ok(classroomService.getClassroomById(id).orElseThrow(()->new IllegalArgumentException("Classroom with "+ id+"not exist")));
    }

    @PostMapping
    public ResponseEntity<ClassroomDto> createClassroom(@RequestBody final ClassroomRequest classroomRequest) throws URISyntaxException {
        ClassroomDto classroomDB = classroomService.save(classroomRequest);
        return ResponseEntity.created(new URI("/admin/classrooms"+classroomDB.id())).body(classroomDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomDto> editClassroom(@PathVariable final Integer id, @RequestBody final ClassroomRequest classroomRequest){
        return ResponseEntity
                .ok()
                .body(classroomService.edit(classroomRequest,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id){
        classroomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
