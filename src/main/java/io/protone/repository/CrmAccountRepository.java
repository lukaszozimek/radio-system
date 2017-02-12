package io.protone.repository;

import io.protone.domain.CrmAccount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmAccount entity.
 */
@SuppressWarnings("unused")
public interface CrmAccountRepository extends JpaRepository<CrmAccount,Long> {

}
