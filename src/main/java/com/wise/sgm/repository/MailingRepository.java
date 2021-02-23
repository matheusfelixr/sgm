package com.wise.sgm.repository;

import com.wise.sgm.model.domain.Mailing;
import com.wise.sgm.model.domain.MailingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailingRepository extends JpaRepository<Mailing, Long> {
    /**
     * Pega mailing que o status está nullo e que não foi enviado para um usuario
     * @return
     */
    List<Mailing> findByMailingStatusIsNullAndDateSentToUserIsNullOrderByDataControlCreateDate();
}
