package org.am.core.web.dto.candidate;

import java.util.Date;

public record StudentCandidateDto(String id,
                                  String idNumber,
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
                                  String password,
                                  String state) {

}
