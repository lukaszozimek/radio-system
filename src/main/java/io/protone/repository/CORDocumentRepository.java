package io.protone.repository;

import io.protone.domain.CORDocument;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORDocument entity.
 */
@SuppressWarnings("unused")
public interface CORDocumentRepository extends JpaRepository<CORDocument,Long> {

}
