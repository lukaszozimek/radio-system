package io.protone.core.repository;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CorChannel entity.
 */
@SuppressWarnings("unused")
public interface CorChannelRepository extends JpaRepository<CorChannel, Long> {
    CorChannel findOneByOrganization_ShortcutAndShortcut(String organization, String shortcut);

    void deleteByShortcutAndOrganization_Shortcut(String shortcut, String network);

    List<CorChannel> findAllByNetwork(CorNetwork network);

    Slice<CorChannel> findSliceByOrganization_Shortcut(String network, Pageable pagable);

}
