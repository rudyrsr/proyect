package org.am.core.web.domain.document.candidate;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "candidate")
@NoArgsConstructor
@Getter
@Setter
public class StudentCandidate {
    @Id
    private String id;


    @Field("ID_numbre")
    private String idNumber;

    @Field("issued_in")
    private String issuedIn;

    @Field("date_Of_birth")
    private Date dateOfBirth;

    @Field("firstN_names")
    private String firstNames;

    @Field("paternal_last_name")
    private String paternalLastName;

    @Field("maternal_last_name")
    private String maternalLastName;

    private String nationality;

    private String gender;

    @Field("cell_phone_number")
    private String cellPhoneNumber;

    private String email;

    @Field("current_address")
    private String currentAddress;

    private String code;

    @Field("path_profile")
    private String pathProfile;

    @Field("path_ci")
    private String pathCi;

    @Field("path_diploma")
    private String pathDiploma;

    @Field("career_id")
    private Integer careerId;

    private String state;

    private String password;

    public StudentCandidate(String idNumber,
                            String issuedIn,
                            Date dateOfBirth,
                            String firstNames,
                            String paternalLastName,
                            String maternalLastName,
                            String nationality,
                            String gender,
                            String cellPhoneNumber,
                            String email,
                            String currentAddress,
                            String code,
                            String pathProfile,
                            String pathCi,
                            String pathDiploma,
                            Integer careerId,
                            String state,
                            String password) {
        this.idNumber = idNumber;
        this.issuedIn = issuedIn;
        this.dateOfBirth = dateOfBirth;
        this.firstNames = firstNames;
        this.paternalLastName = paternalLastName;
        this.maternalLastName = maternalLastName;
        this.nationality = nationality;
        this.gender = gender;
        this.cellPhoneNumber = cellPhoneNumber;
        this.email = email;
        this.currentAddress = currentAddress;
        this.code = code;
        this.pathProfile = pathProfile;
        this.pathCi = pathCi;
        this.pathDiploma = pathDiploma;
        this.careerId = careerId;
        this.state = state;
        this.password = password;
    }
}
