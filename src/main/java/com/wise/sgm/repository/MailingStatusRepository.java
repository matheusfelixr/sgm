package com.wise.sgm.repository;

import com.wise.sgm.model.domain.MailingStatus;
import com.wise.sgm.model.enums.ReasonMailingEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailingStatusRepository extends JpaRepository<MailingStatus, Long> {

    List<MailingStatus> findByReasonMailing(ReasonMailingEnum reasonMailing);

}
