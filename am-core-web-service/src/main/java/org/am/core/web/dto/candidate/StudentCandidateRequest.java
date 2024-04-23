package org.am.core.web.dto.candidate;

import java.util.Date;

public record StudentCandidateRequest(String idNumber,
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
                                      Integer careerId,
                                      String password) {

}
