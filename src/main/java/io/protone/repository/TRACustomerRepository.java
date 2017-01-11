package io.protone.repository;

import io.protone.domain.TRACustomer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TRACustomer entity.
 */
@SuppressWarnings("unused")
public interface TRACustomerRepository extends JpaRepository<TRACustomer,Long> {

    @Query("select tRACustomer from TRACustomer tRACustomer where tRACustomer.account.login = ?#{principal.username}")
    List<TRACustomer> findByAccountIsCurrentUser();

}
