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
    List<CorFilter> findByNetwork(CorNetwork corNetwork);

    Slice<CorFilter> findSliceByNetwork_ShortcutAndTypeAndCorUser_Login(String corNetwork, CorEntityTypeEnum type, String login, Pageable pageable);

    CorFilter findOneByIdAndNetwork_ShortcutAndTypeAndCorUser_Login(Long id, String corNetwork, CorEntityTypeEnum type, String login);

    void deleteByIdAndNetwork_Shortcut(Long id, String corNetwork);

}
