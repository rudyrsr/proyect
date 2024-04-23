package org.am.core.web.repository.jdbc.professor.implement;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.professor.ProfessorDto;
import org.am.core.web.repository.jdbc.professor.ProfessorJdbcRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProfessorJdbcRepositoryImpl implements ProfessorJdbcRepository {
    private final JdbcClient jdbcClient;
    @Override
    public List<ProfessorDto> getProfessorsByAreaIdAndActive(Integer areaId, Boolean active) {
        return jdbcClient.sql("""
                SELECT p.id, p.name, p.last_name, p.second_last_name, p.email
                FROM professor p
                INNER JOIN area_professor ap ON ap.professor_id = p.id
                WHERE ap.area_id=? AND ap.active=?;
                """)
                .param(areaId)
                .param(active)
                .query(ProfessorDto.class)
                .list();
    }
}
