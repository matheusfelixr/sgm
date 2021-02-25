package com.wise.sgm.repository;

import com.wise.sgm.model.domain.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, Long>  {

    UserAuthentication findByUserNameAndCancellationCancellationDateIsNull(String userName);

    UserAuthentication findByEmailAndCancellationCancellationDateIsNull(String email);
}
