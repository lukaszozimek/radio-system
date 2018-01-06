package io.protone.traffic.repository;

import io.protone.traffic.domain.TraCompany;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lukaszozimek on 11/08/2017.
 */
public interface TraCompanyRepository extends JpaRepository<TraCompany, Long> {
    Slice<TraCompany> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(String organization, String channelShortcut, Pageable pageable);

    TraCompany findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);
}
