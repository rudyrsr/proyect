package org.am.core.web.domain.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.admingeneral.Career;
import org.am.core.web.domain.entity.admingeneral.Curriculum;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="itinerary")
public class Itinerary {

    @Id
    @SequenceGenerator(name = "itinerary_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itinerary_sequence")
    private Integer id;

    private String name;

    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;

    public Itinerary(String name, boolean active, Curriculum curriculum) {
        this.name = name;
        this.active = active;
        this.curriculum = curriculum;
    }

}
