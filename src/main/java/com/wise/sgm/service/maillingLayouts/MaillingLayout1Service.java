package com.wise.sgm.service.maillingLayouts;

import com.wise.sgm.model.domain.maillingLayouts.MaillingLayout1;
import com.wise.sgm.repository.maillingLayouts.MaillingLayout1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaillingLayout1Service {

    @Autowired
    private MaillingLayout1Repository maillingLayout1Repository;

    public MaillingLayout1 save(MaillingLayout1 maillingLayout1){
        //verificar que ja existe
        return maillingLayout1Repository.save(maillingLayout1);
    }
}
