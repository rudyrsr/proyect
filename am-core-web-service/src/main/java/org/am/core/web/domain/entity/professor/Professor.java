package org.am.core.web.domain.entity.professor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "professor")
@NoArgsConstructor
@Getter
@Setter
public class Professor {
    @Id
    @SequenceGenerator(name = "professor_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professor_sequence")
    private Integer id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "second_last_name")
    private String secondLastName;

    private String email;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<AreaProfessor> areaProfessorList = new ArrayList<>();

    public Professor(String name, String lastName, String secondLastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.email = email;
    }
}
