package com.wise.sgm.service;

import com.wise.sgm.model.domain.MailingStatus;
import com.wise.sgm.model.domain.UserAuthentication;
import com.wise.sgm.model.enums.ReasonMailingEnum;
import com.wise.sgm.repository.MailingStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MailingStatusService {

    @Autowired
    private MailingStatusRepository mailingStatusRepository;

    public MailingStatus create(MailingStatus mailingStatus, UserAuthentication currentUser) throws Exception{
        mailingStatus.getDataControl().markCreate(currentUser);
        return this.save(mailingStatus);
    }

    public MailingStatus save(MailingStatus mailingStatus){
        return mailingStatusRepository.save(mailingStatus);
    }

    public List<MailingStatus> findByReasonMailing(ReasonMailingEnum reasonMailing ){
        return mailingStatusRepository.findByReasonMailing(reasonMailing);
    }

    public Optional<MailingStatus> findById(Long idMailingStatus ){
        return mailingStatusRepository.findById(idMailingStatus);
    }

}