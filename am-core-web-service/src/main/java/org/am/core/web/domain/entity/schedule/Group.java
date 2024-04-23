package org.am.core.web.domain.entity.schedule;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.admingeneral.AcademicPeriod;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="class_group")
public class Group {
    @Id
    @SequenceGenerator(name = "class_group_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_group_sequence")
    private Integer id;

    private String identifier;
    private String remark;
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "curriculum_id", referencedColumnName = "curriculum_id"),
            @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    })
    private SubjectCurriculum subjectCurriculum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_period_id")
    private AcademicPeriod academicPeriod;

    @OneToMany(mappedBy="group", cascade = CascadeType.ALL)
    private Set<Schedule> scheduleSetList = new HashSet<>();

    public Group(String identifier,
                 String remark,
                 boolean active,
                 SubjectCurriculum subjectCurriculum,
                 Itinerary itinerary,
                 AcademicPeriod academicPeriod,
                 Set<Schedule> scheduleSetList) {
        this.identifier = identifier;
        this.remark = remark;
        this.active = active;
        this.subjectCurriculum = subjectCurriculum;
        this.itinerary = itinerary;
        this.academicPeriod =  academicPeriod;
        this.scheduleSetList = scheduleSetList;
    }
}
