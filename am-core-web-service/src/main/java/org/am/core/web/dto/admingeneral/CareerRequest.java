package org.am.core.web.dto.admingeneral;

import java.time.LocalDate;

public record CareerRequest (String name, String initials,
                             String description, LocalDate creationDate, Integer areaId){

}
