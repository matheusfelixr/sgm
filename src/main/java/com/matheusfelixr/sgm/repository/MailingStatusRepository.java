package com.matheusfelixr.sgm.repository;

import com.matheusfelixr.sgm.model.enums.ReasonMailingEnum;
import com.matheusfelixr.sgm.model.domain.MailingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailingStatusRepository extends JpaRepository<MailingStatus, Long> {

    List<MailingStatus> findByReasonMailing(ReasonMailingEnum reasonMailing);

}
