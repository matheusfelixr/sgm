package com.wise.sgm.service.mailingLayouts;

import com.wise.sgm.model.domain.ImportMailingFile;
import com.wise.sgm.model.domain.Mailing;
import com.wise.sgm.model.domain.UserAuthentication;
import com.wise.sgm.model.domain.mailingLayouts.MailingLayout1;
import com.wise.sgm.repository.mailingLayouts.MailingLayout1Repository;
import com.wise.sgm.service.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailingLayout1Service {

    @Autowired
    private MailingLayout1Repository mailingLayout1Repository;

    @Autowired
    private MailingService mailingService;

    public MailingLayout1 saveNew(MailingLayout1 mailingLayout1) {
        //verificar que ja existe
        return this.save(mailingLayout1);
    }

    public MailingLayout1 save(MailingLayout1 mailingLayout1) {
        return mailingLayout1Repository.save(mailingLayout1);
    }

    public void cancelByImportMailingFile(ImportMailingFile importMailingFile, UserAuthentication cancellationUser, String observationCancel) throws Exception {
        List<MailingLayout1> mailingLayout1s = this.findByImportMailingFile(importMailingFile);
        if (!mailingLayout1s.isEmpty()) {
            for (MailingLayout1 mailingLayout1 : mailingLayout1s) {
                List<Mailing> mailings = this.mailingService.findByMailingLayout1(mailingLayout1);
                if (!mailings.isEmpty()) {
                    for (Mailing mailing : mailings) {
                        mailing.getCancellation().markCanceled(observationCancel, cancellationUser);
                        mailing.getDataControl().markModified(cancellationUser);
                        this.mailingService.save(mailing);
                    }
                }
                mailingLayout1.getCancellation().markCanceled(observationCancel, cancellationUser);
                mailingLayout1.getDataControl().markModified(cancellationUser);
                this.save(mailingLayout1);
            }
        }
    }


    public List<MailingLayout1> findByImportMailingFile(ImportMailingFile importMailingFile) {
        return mailingLayout1Repository.findByImportMailingFile(importMailingFile);
    }

}
