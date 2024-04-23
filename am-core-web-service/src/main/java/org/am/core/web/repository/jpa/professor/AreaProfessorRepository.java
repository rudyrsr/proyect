package org.am.core.web.repository.jpa.professor;

import org.am.core.web.domain.entity.professor.AreaProfessor;
import org.am.core.web.domain.entity.professor.AreaProfessorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AreaProfessorRepository extends JpaRepository<AreaProfessor, AreaProfessorId> {

    void deleteAreaProfessorByArea_IdAndProfessorId(Integer areaId, Integer professorId);

    @Modifying
    @Query(value = "update area_professor set active = false where area_id=:areaId and professor_id=:professorId",
            nativeQuery = true)
    void logicalDeleteByAreaIdAndProfessorId(Integer areaId, Integer professorId);

    List<AreaProfessor> findAllByAreaIdAndActive(Integer areaId, Boolean active);
}
