package com.wise.sgm.repository;

import com.wise.sgm.model.domain.MaillingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaillingTypeRepository extends JpaRepository<MaillingType, Long> {

    MaillingType findByLayout(String layout);
}
