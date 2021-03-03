package com.matheusfelixr.sgm.repository;

import com.matheusfelixr.sgm.model.domain.MailingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingTypeRepository extends JpaRepository<MailingType, Long> {

    MailingType findByLayout(String layout);
}
