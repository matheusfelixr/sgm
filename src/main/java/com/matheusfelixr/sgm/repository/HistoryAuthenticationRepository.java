package com.matheusfelixr.sgm.repository;

import com.matheusfelixr.sgm.model.domain.HistoryAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryAuthenticationRepository extends JpaRepository<HistoryAuthentication, Long>  {

}
