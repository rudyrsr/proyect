package org.am.core.web.domain.entity.schedule;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="group_itinerary")
public class GroupItinerary {

    @Id
    @SequenceGenerator(name = "group_itinerary_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_itinerary_sequence")
    private Integer id;

    private String identifier;

    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "curriculum_id", referencedColumnName = "curriculum_id"),
            @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    })
    private SubjectCurriculum subjectCurriculum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    @OneToMany(mappedBy="groupItinerary", cascade = CascadeType.MERGE)
    private List<ScheduleItinerary> scheduleItineraries;

    public GroupItinerary(String identifier,
                          String remark,
                          SubjectCurriculum subjectCurriculum,
                          Itinerary itinerary,
                          List<ScheduleItinerary> scheduleItineraries) {
        this.identifier = identifier;
        this.remark = remark;
        this.subjectCurriculum = subjectCurriculum;
        this.itinerary = itinerary;
        this.scheduleItineraries = scheduleItineraries;
    }
}
