package com.wise.sgm.repository;

import com.wise.sgm.model.domain.Mailing;
import com.wise.sgm.model.domain.MailingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingRepository extends JpaRepository<Mailing, Long> {

}
