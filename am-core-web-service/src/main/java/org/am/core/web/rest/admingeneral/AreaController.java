package org.am.core.web.rest.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.admingeneral.AreaDto;
import org.am.core.web.dto.admingeneral.AreaRequest;
import org.am.core.web.service.admingeneral.AreaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/areas")
@RequiredArgsConstructor    // generate code with required arguments constructor
public class AreaController {
    private final AreaService areaService;

    @GetMapping
    public ResponseEntity<List<AreaDto>> findAllActive() {
        return ResponseEntity.ok().body(areaService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDto> getAreaById(@PathVariable final Integer id) {
        return ResponseEntity
                .ok()
                .body(areaService.getAreaById(id).orElseThrow(IllegalArgumentException::new));
    }

    @PostMapping
    public ResponseEntity<AreaDto> create(@RequestBody final AreaRequest areaRequest) throws URISyntaxException {
        AreaDto areaDto = areaService.save(areaRequest);

        return ResponseEntity
                .created(new URI("/admin/areas/" + areaDto.id()))
                .body(areaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaDto> edit(@RequestBody final AreaDto dto,
                                        @PathVariable final Integer id) {
        if (dto.id() == null) {
            throw new IllegalArgumentException("Invalid area id, null value");
        }
        if (!Objects.equals(dto.id(), id)) {
            throw new IllegalArgumentException("Invalid id");
        }

        return ResponseEntity
                .ok()
                .body(areaService.edit(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id) {
        areaService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
