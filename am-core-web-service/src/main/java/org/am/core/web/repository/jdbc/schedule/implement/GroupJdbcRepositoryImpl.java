package org.am.core.web.repository.jdbc.schedule.implement;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.schedule.GroupAuxDto;
import org.am.core.web.repository.jdbc.schedule.GroupJdbcRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroupJdbcRepositoryImpl implements GroupJdbcRepository {

    private final JdbcClient jdbcClient;

    @Override
    public List<GroupAuxDto> getGroupsDetailedByCareer(Integer careerId, Integer academicPiriodId) {
        return jdbcClient.sql("""
                select g.id AS groupItineraryId,
                       sc.level,
                       s.name as subjectName,
                       s.initials as subjectInitials,
                       g.identifier as groupIdentifier,
                       g.remark as groupRemark,
                       sh.weekday as dayOfWeek,
                       sh.start_time as startTime,
                       sh.end_time as endTime,
                       sh.assistant,
                       c.name as classroomName,
                       c.initials as classroomInitials,
                       p.name as professorName,
                       p.last_name as professorLastName,
                       p.second_last_name as professorSecondLastName,
                       sh.id as scheduleItineraryId,
                       g.curriculum_id as curriculumId,
                       g.subject_id as subjectId,
                       p.id as professorId,
                       c.id as classroomId
                from class_group g
                         join public.schedule sh on g.id = sh.group_id
                         left join public.classroom c on c.id = sh.classroom_id
                         left join public.professor p on p.id = sh.professor_id
                         join public.subject_curriculum sc on sc.curriculum_id = g.curriculum_id and sc.subject_id = g.subject_id
                                  inner join public.subject s on sc.subject_id = s.id
                                  inner join public.curriculum cu on cu.id = g.curriculum_id
                where cu.career_id = ? and g.academic_period_id = ? and sc.active = true
                order by sc.level, s.name, g.identifier, sh.weekday, sh.start_time;
                """)
                .param(careerId)
                .param(academicPiriodId)
                .query(GroupAuxDto.class)
                .list();
    }
    @Override
    public String suggestGroupIdentifier(Integer subjectId, Integer curriculumId) {
        List<String> identifierGroupList =
                jdbcClient.sql("""
                     SELECT cg.identifier
                     FROM class_group cg
                     WHERE cg.curriculum_id=? and cg.subject_id=? order by cg.identifier DESC
                     """)
                    .param(curriculumId)
                    .param(subjectId)
                    .query(String.class)
                    .list();
        if (identifierGroupList.isEmpty()) {
            return "1";
        }

        Optional<Integer> maxIdentifierValue = identifierGroupList.stream()
                .filter(StringUtils::isNumeric)
                .map(value -> Integer.parseInt(value) + 1)
                .findFirst();

        return maxIdentifierValue.map(Object::toString).orElse("");
    }
}
