package org.am.core.web.dto.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Area;

public record ClassroomRequest(String initials, String name, String type,
                               String address, Integer areaId) {
}
