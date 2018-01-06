package io.protone.core.service;

import io.protone.core.domain.*;
import io.protone.core.repository.CorUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class CorSearchUserService {

    private final Logger log = LoggerFactory.getLogger(CorSearchUserService.class);

    @Autowired
    private CorUserRepository userRepository;

    public Optional<CorUser> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    public Optional<CorUser> getUserWithAuthoritiesByLoginAndOrganizationShortcut(String login, String corOrganizationShortcut) {
        return userRepository.findOneWithAuthoritiesByLoginAndOrganization_Shortcut(login, corOrganizationShortcut);
    }
}
