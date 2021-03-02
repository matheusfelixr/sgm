package com.wise.sgm.repository.mailingLayouts;

import com.wise.sgm.model.domain.ImportMailingFile;
import com.wise.sgm.model.domain.mailingLayouts.MailingLayout1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailingLayout1Repository extends JpaRepository<MailingLayout1, Long> {

    List<MailingLayout1> findByImportMailingFile(ImportMailingFile importMailingFile);
}
