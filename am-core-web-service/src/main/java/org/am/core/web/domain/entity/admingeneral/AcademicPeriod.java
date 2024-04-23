package org.am.core.web.domain.entity.admingeneral;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "academic_period")
@NoArgsConstructor
@Getter
@Setter
public class AcademicPeriod {
    @Id
    @SequenceGenerator(name = "academic_period_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "academic_period_sequence")
    private Integer id;
    private Integer year;
    private String name;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    private Boolean active;
    @Column(name = "enrollment_cost")
    private BigDecimal enrollmentCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    public AcademicPeriod(Integer year, String name,
                          LocalDate startDate, LocalDate endDate,
                          Boolean active, BigDecimal enrollmentCost, Area area) {
        this.year = year;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.enrollmentCost = enrollmentCost;
        this.area = area;
    }
}
