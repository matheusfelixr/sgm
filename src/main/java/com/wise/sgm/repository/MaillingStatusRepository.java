package com.wise.sgm.repository;

import com.wise.sgm.model.domain.MaillingStatus;
import com.wise.sgm.model.enums.ReasonMaillingEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaillingStatusRepository extends JpaRepository<MaillingStatus, Long> {

    List<MaillingStatus> findByReasonMailling(ReasonMaillingEnum reasonMailling);

}
