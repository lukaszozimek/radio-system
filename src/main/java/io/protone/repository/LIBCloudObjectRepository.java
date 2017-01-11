package io.protone.repository;

import io.protone.domain.LIBCloudObject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LIBCloudObject entity.
 */
@SuppressWarnings("unused")
public interface LIBCloudObjectRepository extends JpaRepository<LIBCloudObject,Long> {

    @Query("select lIBCloudObject from LIBCloudObject lIBCloudObject where lIBCloudObject.createdBy.login = ?#{principal.username}")
    List<LIBCloudObject> findByCreatedByIsCurrentUser();

}
