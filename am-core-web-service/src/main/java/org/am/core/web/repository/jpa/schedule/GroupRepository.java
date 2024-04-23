package org.am.core.web.repository.jpa.schedule;

import org.am.core.web.domain.entity.schedule.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    Group findByIdentifierAndRemark (String groupIdentifier, String groupRemark );
}
