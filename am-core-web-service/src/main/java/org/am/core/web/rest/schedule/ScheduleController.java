package org.am.core.web.rest.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.service.schedule.ScheduleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/areas/{areaId}/careers/{careerId}/academic-periods/{academicPeriodId}/groups/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
}
