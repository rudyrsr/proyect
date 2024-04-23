package org.am.core.web.dto.admingeneral;

import java.time.LocalDate;

public record CareerDto (Integer id, String name, String initials,
                         String description, LocalDate creationDate) {
}
