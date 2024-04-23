package org.am.core.web.dto.professor;

public record ProfessorDto(Integer id,
                           String name,
                           String lastName,
                           String secondLastName,
                           String email) {
}
