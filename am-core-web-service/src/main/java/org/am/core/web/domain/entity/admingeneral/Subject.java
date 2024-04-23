package org.am.core.web.domain.entity.admingeneral;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "subject")
@NoArgsConstructor
@Getter
@Setter
public class Subject {
    @Id
    @SequenceGenerator(name = "subject_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_sequence")
    private Integer id;
    private String name;
    private String initials;
    private Boolean active;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    public Subject(String name, String initials, Boolean active, Area area) {
        this.name = name;
        this.initials = initials;
        this.active = active;
        this.area = area;
    }
}
