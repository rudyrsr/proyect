package org.am.core.web.repository.jpa.professor;

import org.am.core.web.domain.entity.professor.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
}
