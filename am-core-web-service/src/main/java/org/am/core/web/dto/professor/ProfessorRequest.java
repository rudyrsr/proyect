package org.am.core.web.dto.professor;

public record ProfessorRequest(String name,
                               String lastName,
                               String secondLastName,
                               String email,
                               Integer areaId) {
}
