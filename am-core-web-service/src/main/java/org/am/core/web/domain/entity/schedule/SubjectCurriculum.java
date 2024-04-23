package org.am.core.web.domain.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.am.core.web.domain.entity.admingeneral.Curriculum;
import org.am.core.web.domain.entity.admingeneral.Subject;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="subject_curriculum")
public class SubjectCurriculum {

    @EmbeddedId
    private SubjectCurriculumId subjectCurriculumId;

    private Short level;

    private Boolean optional;

    private String path;

    private Short workload;

    private Boolean active;

    @Column(name = "level_name")
    private String levelName;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("curriculumId")
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public SubjectCurriculum(SubjectCurriculumId subjectCurriculumId, Short level, Boolean optional,
                             String path, Short workload, Boolean active, String levelName,
                             Curriculum curriculum, Subject subject) {
        this.subjectCurriculumId = subjectCurriculumId;
        this.level = level;
        this.optional = optional;
        this.path = path;
        this.workload = workload;
        this.active = active;
        this.levelName = levelName;
        this.curriculum = curriculum;
        this.subject = subject;
    }

}
