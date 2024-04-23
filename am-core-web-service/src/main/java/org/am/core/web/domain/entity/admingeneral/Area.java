package org.am.core.web.domain.entity.admingeneral;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.professor.AreaProfessor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "area")
@NoArgsConstructor
@Getter
@Setter
public class Area {
    @Id
    @SequenceGenerator(name = "area_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "area_sequence")
    private Integer id;
    private String name;
    private String initials;
    private Boolean active;
    private String description;
    @OneToMany(mappedBy="area", cascade = CascadeType.ALL)
    private List<AreaProfessor> areaProfessorList = new ArrayList<>();

    public Area(String name, String initials, Boolean active, String description) {
        this.name = name;
        this.initials = initials;
        this.active = active;
        this.description = description;
    }
}
