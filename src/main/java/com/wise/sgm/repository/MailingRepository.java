package com.wise.sgm.repository;

import com.wise.sgm.model.domain.Mailing;
import com.wise.sgm.model.domain.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailingRepository extends JpaRepository<Mailing, Long> {
    /**
     * Pega mailling que o status está nullo e que não foi enviado para um usuario e que nao esta cancelado
     * @return
     */
    List<Mailing> findByMailingStatusIsNullAndDateSentToUserIsNullAndCancellationCancellationDateIsNullOrderByDataControlCreateDate();

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

}
