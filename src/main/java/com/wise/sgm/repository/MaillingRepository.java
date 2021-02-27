package com.wise.sgm.repository;

import com.wise.sgm.model.domain.Mailling;
import com.wise.sgm.model.domain.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaillingRepository extends JpaRepository<Mailling, Long> {
    /**
     * Pega mailling que o status está nullo e que não foi enviado para um usuario e que nao esta cancelado
     * @return
     */
    List<Mailling> findByMaillingStatusIsNullAndDateSentToUserIsNullAndCancellationCancellationDateIsNullOrderByDataControlCreateDate();

    /**
     * Pega mailling por usuario que iniciou atendimento e que o status está nullo e que não foi enviado para um usuario e que nao esta cancelado
     * @return
     */
    List<Mailling> findBySentToUserAndMaillingStatusIsNullAndDateSentToUserIsNotNullAndCancellationCancellationDateIsNullOrderByDataControlCreateDate(UserAuthentication sentToUser);


    /**
     * Pega mailling que iniciou atendimento e que o status está nullo e que não foi enviado para um usuario e que nao esta cancelado
     * @return
     */
    List<Mailling> findBySentToUserIsNullAndMaillingStatusIsNullAndDateSentToUserIsNotNullAndCancellationCancellationDateIsNullOrderByDataControlCreateDate();

}
