package io.protone.core.repository;

import io.protone.core.domain.CorFilter;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.enumeration.CorEntityTypeEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lukaszozimek on 20.07.2017.
 */
public interface CorFilterRepository extends JpaRepository<CorFilter, Long> {
    List<CorFilter> findByChannel_Organization_ShortcutAndChannel_Shortcut(String organizationShortcut, String channelShortcut);

    Slice<CorFilter> findSliceByChannel_Organization_ShortcutAndChannel_ShortcutAndTypeAndCorUser_Login(String organizationShortcut, String channelShortcut, CorEntityTypeEnum type, String login, Pageable pageable);

    CorFilter findOneByIdAndChannel_Organization_ShortcutAndChannel_ShortcutAndTypeAndCorUser_Login(Long id, String organizationShortcut, String channelShortcut, CorEntityTypeEnum type, String login);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organizationShortcut, String channelShortcut);

}
