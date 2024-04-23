package org.am.core.web.repository.jdbc.schedule;

import org.am.core.web.dto.schedule.GroupAuxDto;

import java.util.List;

public interface GroupJdbcRepository {

    List<GroupAuxDto> getGroupsDetailedByCareer(Integer careerId, Integer academicPeriodId);
    String suggestGroupIdentifier (Integer subjectId, Integer curriculumId);

}
