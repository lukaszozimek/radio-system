package io.protone.repository;

import io.protone.domain.CORChannel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORChannel entity.
 */
@SuppressWarnings("unused")
public interface CORChannelRepository extends JpaRepository<CORChannel,Long> {

}
