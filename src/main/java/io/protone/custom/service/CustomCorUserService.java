package io.protone.custom.service;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import io.protone.custom.service.dto.CoreNetworkPT;
import io.protone.custom.service.dto.CoreUserPT;
import io.protone.custom.service.mapper.CustomCorNetworkMapper;
import io.protone.custom.service.mapper.CustomCorUserMapperExt;
import io.protone.domain.CorAuthority;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorUser;
import io.protone.repository.custom.CustomAuthorityRepository;
import io.protone.repository.custom.CustomCorNetworkRepository;
import io.protone.repository.custom.CustomCorUserRepository;
import io.protone.security.AuthoritiesConstants;
import io.protone.security.SecurityUtils;
import io.protone.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class CustomCorUserService {

    private final Logger log = LoggerFactory.getLogger(CustomCorUserService.class);
    @Autowired
    private CustomCorUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomAuthorityRepository authorityRepository;

    @Autowired
    private CustomCorNetworkRepository customCorNetworkRepository;
    @Autowired
    private CustomCorUserMapperExt customCorUserMapperExt;
    @Autowired
    private CustomCorNetworkMapper customCorNetworkMapper;


    public Optional<CorUser> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        Optional<CorUser> corUser = userRepository.findOneByActivationkey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationkey(null);
                log.debug("Activated user: {}", user);
                return user;
            });
        if (corUser.isPresent()) {
            CorNetwork network = customCorNetworkRepository.save(corUser.get().getNetworks().stream().findFirst().get().active(true));

        }
        return corUser;
    }

    public Optional<CorUser> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);

        return userRepository.findOneByResetkey(key)
            .filter(user -> {
                ZonedDateTime oneDayAgo = ZonedDateTime.now().minusHours(24);
                return user.getResetdate().isAfter(oneDayAgo);
            })
            .map(user -> {
                user.setPasswordhash(passwordEncoder.encode(newPassword));
                user.setResetkey(null);
                user.setResetdate(null);
                return user;
            });
    }

    public Optional<CorUser> requestPasswordReset(String mail) {
        return userRepository.findOneByEmail(mail)
            .filter(CorUser::isActivated)
            .map(user -> {
                user.setResetkey(RandomUtil.generateResetKey());
                user.setResetdate(ZonedDateTime.now());
                return user;
            });
    }

    public CorUser createUser(String login, String password, String firstName, String lastName, String email,
                              String imageUrl, String langKey, CoreNetworkPT networkPt) {

        CorUser newUser = new CorUser();
        CorNetwork network = customCorNetworkRepository.save(customCorNetworkMapper.cORNetworkDTOToCorNetwork(networkPt));
        CorAuthority authority = authorityRepository.findOne(AuthoritiesConstants.USER);
        Set<CorAuthority> authorities = new HashSet<>();

        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        // new user gets initially a generated password
        newUser.setPasswordhash(encryptedPassword);
        newUser.setFirstname(firstName);
        newUser.setLastname(lastName);
        newUser.setEmail(email);
        newUser.setImageurl(imageUrl);
        if (Strings.isNullOrEmpty(langKey)) {
            newUser.setLangkey("en");
        } else {
            newUser.setLangkey(langKey);

        }

        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationkey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        newUser.setNetworks(newHashSet(network));
        userRepository.save(newUser);
        network.setNetworkUsers(newUser);
        customCorNetworkRepository.save(network);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public CorUser createUser(CoreUserPT userDTO) {
        CorUser user = new CorUser();
        user.setLogin(userDTO.getLogin());
        user.setFirstname(userDTO.getFirstName());
        user.setLastname(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageurl(userDTO.getImageurl());
        if (userDTO.getLangKey() == null) {
            user.setLangkey("en"); // default language
        } else {
            user.setLangkey(userDTO.getLangKey());
        }
        if (userDTO.getAuthorities() != null) {
            Set<CorAuthority> authorities = new HashSet<>();
            userDTO.getAuthorities().forEach(
                authority -> authorities.add(authorityRepository.findOne(authority))
            );
            user.setAuthorities(authorities);
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPasswordhash(encryptedPassword);
        user.setResetkey(RandomUtil.generateResetKey());
        user.setResetdate(ZonedDateTime.now());
        user.setActivated(true);
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setEmail(email);
            user.setLangkey(langKey);
            log.debug("Changed Information for User: {}", user);
        });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     */
    public Optional<CoreUserPT> updateUser(CoreUserPT userDTO) {
        return Optional.of(userRepository
            .findOne(userDTO.getId()))
            .map(user -> {
                user.setLogin(userDTO.getLogin());
                user.setFirstname(userDTO.getFirstName());
                user.setLastname(userDTO.getLastName());
                user.setEmail(userDTO.getEmail());
                user.setImageurl(userDTO.getImageurl());
                user.setActivated(userDTO.getActivated());
                user.setLangkey(userDTO.getLangKey());
                Set<CorAuthority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO.getAuthorities().stream()
                    .map(authorityRepository::findOne)
                    .forEach(managedAuthorities::add);
                log.debug("Changed Information for User: {}", user);
                return user;
            }).map(CoreUserPT::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String password) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            String encryptedPassword = passwordEncoder.encode(password);
            user.setPasswordhash(encryptedPassword);
            log.debug("Changed password for User: {}", user);
        });
    }

    @Transactional(readOnly = true)
    public Page<CoreUserPT> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(CoreUserPT::new);
    }

    @Transactional(readOnly = true)
    public Optional<CorUser> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public CorUser getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public CoreUserPT getUserWithAuthorities() {
        CorUser corUser = userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).orElse(null);
        return customCorUserMapperExt.userToCoreUserPT(corUser);
    }


    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        ZonedDateTime now = ZonedDateTime.now();
        List<CorUser> users = userRepository.findAllByActivatedIsFalseAndCreateddateBefore(now.minusDays(3));
        for (CorUser user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
        }
    }
}
