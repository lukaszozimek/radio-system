package io.protone.custom.service;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.common.collect.Sets;
import io.protone.custom.service.dto.CoreNetworkPT;
import io.protone.custom.service.dto.CoreUserPT;
import io.protone.web.rest.mapper.CorChannelMapper;
import io.protone.web.rest.mapper.CorNetworkMapper;
import io.protone.web.rest.mapper.CorUserMapper;
import io.protone.domain.CorAuthority;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorUser;
import io.protone.repository.cor.CorAuthorityRepository;
import io.protone.repository.cor.CorChannelRepository;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.cor.CorUserRepository;
import io.protone.security.AuthoritiesConstants;
import io.protone.security.SecurityUtils;
import io.protone.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import static java.util.stream.Collectors.toList;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class CustomCorUserService {

    private final Logger log = LoggerFactory.getLogger(CustomCorUserService.class);

    @Autowired
    private CorUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CorAuthorityRepository authorityRepository;

    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CorUserMapper customCorUserMapperExt;

    @Autowired
    private CorNetworkMapper customCorNetworkMapper;

    @Autowired
    private CorChannelMapper customCORChannelMapper;


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
            CorNetwork network = corNetworkRepository.save(corUser.get().getNetworks().stream().findFirst().get().active(true));

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
        CorNetwork network = corNetworkRepository.save(customCorNetworkMapper.DTO2DB(networkPt));
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
        network.addNetworkUsers(newUser);
        corNetworkRepository.save(network);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public CorUser createUser(CoreUserPT userDTO) {
        CorUser user = new CorUser();
        CorUser userCreator = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();

        user.setLogin(userDTO.getLogin());
        user.setFirstname(userDTO.getFirstName());
        user.setLastname(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageurl(userDTO.getImageurl());
        user.addNetwork(userCreator.getNetworks().stream().findFirst().get());
        userDTO.getChannel().stream().forEach(coreChannelPT -> {
            user.addChannel(customCORChannelMapper.DTO2DB(coreChannelPT, userCreator.getNetworks().stream().findFirst().get()));

        });
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
    public CoreUserPT updateUser(CoreUserPT userDTO) {

        CorUser corUser = Optional.of(userRepository
            .findOne(userDTO.getId()))
            .map(user -> {
                user.setLogin(userDTO.getLogin());
                user.setFirstname(userDTO.getFirstName());
                user.setLastname(userDTO.getLastName());
                user.setEmail(userDTO.getEmail());
                user.setImageurl(userDTO.getImageurl());
                user.setActivated(userDTO.getActivated());
                user.setLangkey(userDTO.getLangKey());
                user.getChannels().clear();
                Set<CorChannel> corChannels = user.getChannels();
                corChannels.clear();
                userDTO.getChannel().stream()
                    .map(channelPT -> corChannelRepository.findOne(channelPT.getId()))
                    .forEach(corChannels::add);
                Set<CorAuthority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO.getAuthorities().stream()
                    .map(authorityRepository::findOne)
                    .forEach(managedAuthorities::add);
                log.debug("Changed Information for User: {}", user);
                return user;
            }).get();
        corUser = userRepository.saveAndFlush(corUser);
        return customCorUserMapperExt.DB2DTO(corUser);
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
    public List<CoreUserPT> getAllManagedUsers(CorNetwork corNetwork) {
        return userRepository.findByNetworks(Sets.newHashSet(corNetwork)).stream().map(customCorUserMapperExt::DB2DTO).collect(toList());
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
        return customCorUserMapperExt.DB2DTO(corUser);
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

    @Transactional(readOnly = true)
    public Optional<CorUser> getUserWithAuthoritiesByLoginAndNetwork(String login, CorNetwork corNetwork) {
        return userRepository.findOneWithAuthoritiesByLoginAndNetworks(login, Sets.newHashSet(corNetwork));
    }
}
