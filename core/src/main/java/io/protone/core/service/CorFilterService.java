package io.protone.core.service;

import io.protone.core.domain.CorFilter;
import io.protone.core.domain.CorUser;
import io.protone.core.domain.enumeration.CorEntityTypeEnum;
import io.protone.core.repository.CorFilterRepository;
import io.protone.core.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by lukaszozimek on 20.07.2017.
 */
@Service
public class CorFilterService {

    @Autowired
    private CorFilterRepository corFilterRepository;
    @Autowired
    private CorUserService corUserService;

    @Transactional
    public CorFilter save(CorFilter corFilter) {
        Optional<CorUser> corUser = corUserService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin());
        return corFilterRepository.saveAndFlush(corFilter.user(corUser.get()));
    }

    public CorFilter findOne(Long id, CorEntityTypeEnum corEntityTypeEnum, String organizationShortcut) {
        return corFilterRepository.findOneByIdAndNetwork_ShortcutAndTypeAndCorUser_Login(id,organizationShortcut, corEntityTypeEnum, SecurityUtils.getCurrentUserLogin());
    }

    public Slice<CorFilter> findAll(String organizationShortcut, CorEntityTypeEnum corEntityTypeEnum, Pageable pagable) {
        return corFilterRepository.findSliceByNetwork_ShortcutAndTypeAndCorUser_Login(organizationShortcut, corEntityTypeEnum, SecurityUtils.getCurrentUserLogin(), pagable);
    }

    @Transactional
    public void delete(Long id, String organizationShortcut) {
        corFilterRepository.deleteByIdAndNetwork_Shortcut(id, organizationShortcut);
    }
}
