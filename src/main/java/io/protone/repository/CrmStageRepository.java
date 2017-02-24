package io.protone.repository;

import io.protone.domain.CrmStage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmStage entity.
 */
@SuppressWarnings("unused")
public interface CrmStageRepository extends JpaRepository<CrmStage,Long> {

}