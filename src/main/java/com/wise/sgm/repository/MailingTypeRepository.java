package com.wise.sgm.repository;

import com.wise.sgm.model.domain.MailingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingTypeRepository extends JpaRepository<MailingType, Long> {

    MailingType findByLayout(String layout);
}
