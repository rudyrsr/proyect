package org.am.core.web.domain.entity.professor;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class AreaProfessorId implements Serializable {
    @Column(name = "area_id")
    private Integer areaId;
    @Column(name = "professor_id")
    private Integer professorId;

    public AreaProfessorId(Integer areaId, Integer professorId) {
        this.areaId = areaId;
        this.professorId = professorId;
    }
}
