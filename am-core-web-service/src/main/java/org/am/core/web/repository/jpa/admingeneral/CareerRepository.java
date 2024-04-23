package org.am.core.web.repository.jpa.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Career;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CareerRepository extends CrudRepository<Career, Integer> {
    List<Career> findAllByArea_IdAndActiveOrderByName(Integer areaId, Boolean active);
}
