package com.wise.sgm.service.mailingLayouts;

import com.wise.sgm.model.domain.mailingLayouts.MailingLayout1;
import com.wise.sgm.repository.mailingLayouts.MailingLayout1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailingLayout1Service {

    @Autowired
    private MailingLayout1Repository mailingLayout1Repository;

    public MailingLayout1 save(MailingLayout1 mailingLayout1){
        return mailingLayout1Repository.save(mailingLayout1);
    }
}
