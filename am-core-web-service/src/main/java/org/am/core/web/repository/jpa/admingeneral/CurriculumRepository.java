package org.am.core.web.repository.jpa.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {
    List<Curriculum> findCurriculumByCareer_IdAndActiveOrderByName(Integer careerId, Boolean active);

    @Modifying
    @Query(value = "DELETE FROM subject_curriculum WHERE curriculum_id = :curriculumId AND subject_id IN :subjectIds",
            nativeQuery = true)
    void deleteSubjectCurriculumList(@Param("curriculumId") Integer curriculumId,
                                     @Param("subjectIds") List<Integer> subjectIds);
}
