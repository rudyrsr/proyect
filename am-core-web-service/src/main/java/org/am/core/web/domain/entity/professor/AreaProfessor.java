package org.am.core.web.domain.entity.professor;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.admingeneral.Area;

import java.time.LocalDate;

@Entity
@Table(name = "area_professor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AreaProfessor {
    @EmbeddedId
    private AreaProfessorId areaProfessorId;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("professorId")
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("areaId")
    @JoinColumn(name = "area_id")
    private Area area;
}
