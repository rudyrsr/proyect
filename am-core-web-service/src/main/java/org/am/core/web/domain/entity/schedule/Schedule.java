package org.am.core.web.domain.entity.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.admingeneral.Classroom;
import org.am.core.web.domain.entity.professor.Professor;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="schedule")
public class Schedule {
    @Id
    @SequenceGenerator(name = "schedule_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_sequence")
    private Integer id;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "weekday")
    private Integer weekday;

    private String assistant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Schedule (LocalTime startTime,
                     LocalTime endTime,
                     Integer weekday,
                     String assistant,
                     Classroom classroom,
                     Professor professor,
                     Group group) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekday = weekday;
        this.assistant = assistant;
        this.classroom = classroom;
        this.professor = professor;
        this.group = group;
    }
}
