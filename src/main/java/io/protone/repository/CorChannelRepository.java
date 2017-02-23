package io.protone.repository;

import io.protone.domain.CorChannel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorChannel entity.
 */
@SuppressWarnings("unused")
public interface CorChannelRepository extends JpaRepository<CorChannel,Long> {

}
