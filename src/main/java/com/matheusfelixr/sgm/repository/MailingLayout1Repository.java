package com.matheusfelixr.sgm.repository;

import com.matheusfelixr.sgm.model.domain.ImportMailingFile;
import com.matheusfelixr.sgm.model.domain.MailingLayout1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailingLayout1Repository extends JpaRepository<MailingLayout1, Long> {

    List<MailingLayout1> findByImportMailingFile(ImportMailingFile importMailingFile);
}