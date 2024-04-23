package org.am.core.web.repository.jpa.candidate;

import org.am.core.web.domain.document.candidate.StudentCandidate;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface StudentCandidateRepository extends MongoRepository<StudentCandidate, String> {

 }
