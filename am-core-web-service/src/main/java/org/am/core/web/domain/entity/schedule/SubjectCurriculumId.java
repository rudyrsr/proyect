package org.am.core.web.domain.entity.schedule;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SubjectCurriculumId implements Serializable {

    @Column(name = "curriculum_id")
    private Integer curriculumId;

    @Column(name = "subject_id")
    private Integer subjectId;

    public SubjectCurriculumId(Integer curriculumId, Integer subjectId) {
        this.curriculumId = curriculumId;
        this.subjectId = subjectId;
    }
}
