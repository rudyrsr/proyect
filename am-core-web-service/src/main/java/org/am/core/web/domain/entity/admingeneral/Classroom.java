package org.am.core.web.domain.entity.admingeneral;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "classroom")
@NoArgsConstructor
@Getter
@Setter
public class Classroom {
    @Id
    @SequenceGenerator(name = "classroom_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classroom_sequence")
    private Integer id;
    private String initials;
    private String name;
    private String type;
    private String address;
    private Boolean active;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    public Classroom(String initials, String name, String type, Boolean active, Area area, String address) {
        this.initials = initials;
        this.name = name;
        this.type = type;
        this.active = active;
        this.area = area;
        this.address=address;
    }
}
