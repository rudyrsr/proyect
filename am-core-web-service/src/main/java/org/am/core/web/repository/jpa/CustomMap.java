package org.am.core.web.repository.jpa;

public interface CustomMap<DTO, E> {
    DTO toDto(E e);
    E toEntity(DTO dto);
}
