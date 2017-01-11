package io.protone.repository;

import io.protone.domain.CRMAccount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMAccount entity.
 */
@SuppressWarnings("unused")
public interface CRMAccountRepository extends JpaRepository<CRMAccount,Long> {

}
