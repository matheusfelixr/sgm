package com.matheusfelixr.sgm.service;

import com.matheusfelixr.sgm.model.domain.DataControlImpl;
import com.matheusfelixr.sgm.model.domain.MailingLayout1;
import com.matheusfelixr.sgm.repository.MailingLayout1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
public class MailingLayout1Service {

    @Autowired
    private MailingLayout1Repository mailingLayout1Repository;

    @Autowired
    private MailingService mailingService;

    public MailingLayout1 saveNew(MailingLayout1 mailingLayout1) throws Exception {
        DataControlImpl dataControl = mailingLayout1.getDataControl();
        mailingLayout1.setDataControl(null);
        List<MailingLayout1> MailingLayout1s = this.findByExample(mailingLayout1);
        for (MailingLayout1 mailingLayout1For : MailingLayout1s) {
            if (!mailingLayout1For.getCancellation().isCancelled()) {
                throw new ValidationException("Erro ao inserir valor duplicado para o CPF/CNPJ: " + mailingLayout1.getCPF_CNPJ());
            }
        }
        mailingLayout1.setDataControl(dataControl);
        return this.save(mailingLayout1);
    }

    public List<MailingLayout1> findByExample(MailingLayout1 mailingLayout1) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll();
        Example<MailingLayout1> example = Example.<MailingLayout1>of(mailingLayout1, matcher);
        return mailingLayout1Repository.findAll(example);
    }

    public MailingLayout1 save(MailingLayout1 mailingLayout1) {
        return mailingLayout1Repository.save(mailingLayout1);
    }


}
