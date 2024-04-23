package org.am.core.web.rest.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.admingeneral.ScheduleParametersDto;
import org.am.core.web.dto.admingeneral.ScheduleParametersRequest;
import org.am.core.web.service.admingeneral.ScheduleParametersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/schedule-parameters")
@RequiredArgsConstructor
public class ScheduleParameterController {

    private final ScheduleParametersService scheduleParametersService;
    @GetMapping("/{areaId}")
    public ResponseEntity<ScheduleParametersDto> getScheduleParametersById(@PathVariable final Integer areaId) {
        return ResponseEntity
                .ok()
                .body(
                        scheduleParametersService.getScheduleParametersById(areaId)
                                .orElseThrow(IllegalArgumentException::new)
                );
    }

    @PutMapping("/{areaId}")
    public ResponseEntity<ScheduleParametersDto> editScheduleParameters(@PathVariable final Integer areaId,
                                                                        @RequestBody final ScheduleParametersRequest request) {
        return ResponseEntity.ok().body(scheduleParametersService.edit(areaId, request));
    }
}
