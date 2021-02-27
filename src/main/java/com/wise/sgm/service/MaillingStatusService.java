package com.wise.sgm.service;

import com.wise.sgm.model.domain.MaillingStatus;
import com.wise.sgm.model.domain.UserAuthentication;
import com.wise.sgm.model.enums.ReasonMaillingEnum;
import com.wise.sgm.repository.MaillingStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaillingStatusService {

    @Autowired
    private MaillingStatusRepository maillingStatusRepository;

    public MaillingStatus create(MaillingStatus maillingStatus ,UserAuthentication currentUser) throws Exception{
        maillingStatus.getDataControl().markCreate(currentUser);
        return this.save(maillingStatus);
    }

    public MaillingStatus save(MaillingStatus maillingStatus ){
        return maillingStatusRepository.save(maillingStatus);
    }

    public List<MaillingStatus> findByReasonMailling(ReasonMaillingEnum reasonMailling ){
        return maillingStatusRepository.findByReasonMailling(reasonMailling);
    }
}