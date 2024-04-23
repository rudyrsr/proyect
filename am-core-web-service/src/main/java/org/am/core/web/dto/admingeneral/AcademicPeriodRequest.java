package org.am.core.web.dto.admingeneral;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AcademicPeriodRequest(Integer year,
                                    String name,
                                    LocalDate startDate,
                                    LocalDate endDate,
                                    BigDecimal enrollmentCost,
                                    Integer areaId
                                    ){
}
