package com.wise.sgm.repository;

import com.wise.sgm.model.domain.Mailling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaillingRepository extends JpaRepository<Mailling, Long> {
    /**
     * Pega mailling que o status está nullo e que não foi enviado para um usuario
     * @return
     */
    List<Mailling> findByMaillingStatusIsNullAndDateSentToUserIsNullOrderByDataControlCreateDate();
}
