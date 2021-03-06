package com.matheusfelixr.sgm.repository;

import com.matheusfelixr.sgm.model.domain.ImportMailingFile;
import com.matheusfelixr.sgm.model.domain.UserAuthentication;
import com.matheusfelixr.sgm.model.domain.Mailing;
import com.matheusfelixr.sgm.model.domain.MailingLayout1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MailingRepository extends JpaRepository<Mailing, Long> {
    @Query(
            value = "SELECT * FROM mailing\n" +
                    "WHERE date_sent_to_user IS NULL\n" +
                    "AND mailing_status IS NULL\n" +
                    "AND date_sent_to_user IS NULL\n" +
                    "AND cancellation_date IS NULL\n" +
                    "ORDER BY random()\n" +
                    "LIMIT 100",
            nativeQuery = true)
    List<Mailing>  findNextAttendance();

    /**
     * Pega mailling por usuario que iniciou atendimento e que o status está nullo e que não foi enviado para um usuario e que nao esta cancelado
     * @return
     */
    List<Mailing> findBySentToUserAndMailingStatusIsNullAndDateSentToUserIsNotNullAndCancellationCancellationDateIsNullOrderByDataControlCreateDate(UserAuthentication sentToUser);


    /**
     * Pega mailling que iniciou atendimento e que o status está nullo e que não foi enviado para um usuario e que nao esta cancelado
     * @return
     */
    List<Mailing> findBySentToUserIsNullAndMailingStatusIsNullAndDateSentToUserIsNotNullAndCancellationCancellationDateIsNullOrderByDataControlCreateDate();

    List<Mailing> findByMailingLayout1(MailingLayout1 mailingLayout1);

    List<Mailing> findByImportMailingFile(ImportMailingFile importMailingFile);

    List<Mailing> findByEndDateBetweenAndCancellationCancellationDateIsNullAndMailingStatusIsNotNull(Date startDateExport, Date endDateExport);
}

