package io.protone.application.web.api.cor.impl;


import com.codahale.metrics.annotation.Timed;
import io.protone.application.web.api.cor.CorUserResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.vm.KeyAndPasswordVM;
import io.protone.core.api.dto.CorManagedUserDTO;
import io.protone.core.api.dto.CorUserDTO;
import io.protone.core.domain.CorUser;
import io.protone.core.mapper.CorNetworkMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.core.repository.CorOrganizationRepository;
import io.protone.core.repository.CorUserRepository;
import io.protone.core.security.SecurityUtils;
import io.protone.core.service.CorMailService;
import io.protone.core.service.CorUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api/v1/user")
public class CorUserResourceImpl implements CorUserResource {

    private final Logger log = LoggerFactory.getLogger(CorUserResourceImpl.class);

    private final CorUserRepository userRepository;

    private final CorOrganizationRepository corOrganizationRepository;

    private final CorNetworkMapper customCorNetworkMapper;

    private final CorUserService userService;

    private final CorMailService mailService;

    private final CorUserMapper corUserMapper;

    public CorUserResourceImpl(CorUserRepository userRepository, CorUserService userService,
                               CorMailService mailService, CorOrganizationRepository corOrganizationRepository, CorNetworkMapper customCorNetworkMapper, CorUserMapper corUserMapper) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.corOrganizationRepository = corOrganizationRepository;
        this.customCorNetworkMapper = customCorNetworkMapper;
        this.corUserMapper = corUserMapper;
    }

    /**
     * POST  /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @return the ResponseEntity with status 201 (Created) if the user is registered or 400 (Bad Request) if the login or e-mail is already in use
     */
    @PostMapping(path = "/register",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    @Override
    public ResponseEntity registerAccount(@Valid @RequestBody CorManagedUserDTO managedUserVM) {

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);

        return userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase())
                .map(user -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                .orElseGet(() -> userRepository.findOneByEmail(managedUserVM.getEmail())
                        .map(user -> new ResponseEntity<>("e-mail address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                        .orElseGet(() -> corOrganizationRepository.findOneByShortcutOrName(managedUserVM.getOrganizationDTO().getShortcut(), managedUserVM.getOrganizationDTO().getName())
                                .map(user -> new ResponseEntity<>("Organization with this name and shortcut is already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                                .orElseGet(() -> {

                                    CorUser user = userService
                                            .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
                                                    managedUserVM.getFirstName(), managedUserVM.getLastName(),
                                                    managedUserVM.getEmail().toLowerCase(), managedUserVM.getLangKey(), managedUserVM.getOrganizationDTO());


                                    mailService.sendActivationEmail(user);
                                    return new ResponseEntity<>(HttpStatus.CREATED);
                                })
                        ));
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @return the ResponseEntity with status 200 (OK) and the activated user in body, or status 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    @Timed
    @Override
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        return userService.activateRegistration(key)
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    @Timed
    @Override
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in body, or status 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    @Timed
    @Override
    public ResponseEntity<CorUserDTO> getAccount() {
        return Optional.ofNullable(corUserMapper.DB2DTO(userService.getUserWithAuthorities()))
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /account : update the current user information.
     *
     * @param userDTO the current user information
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) or 500 (Internal Server Error) if the user couldn't be updated
     */
    @PostMapping("/account")
    @Timed
    @Override
    public ResponseEntity<String> saveAccount(@Valid @RequestBody CorUserDTO userDTO) {
        Optional<CorUser> existingUser = userRepository.findOneByEmail(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userDTO.getLogin()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "emailexists", "Email already in use")).body(null);
        }
        return userRepository
                .findOneByLogin(SecurityUtils.getCurrentUserLogin())
                .map(u -> {
                    userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                            userDTO.getLangKey());
                    return new ResponseEntity<String>(HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /account/change_password : changes the current user's password
     *
     * @param password the new password
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) if the new password is not strong enough
     */
    @PostMapping(path = "/account/change_password",
            produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    @Override
    public ResponseEntity changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST   /account/reset_password/init : Send an e-mail to reset the password of the user
     *
     * @param mail the mail of the user
     * @return the ResponseEntity with status 200 (OK) if the e-mail was sent, or status 400 (Bad Request) if the e-mail address is not registered
     */
    @PostMapping(path = "/account/reset_password/init",
            produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    @Override
    public ResponseEntity requestPasswordReset(@RequestBody String mail) {
        return userService.requestPasswordReset(mail)
                .map(user -> {
                    mailService.sendPasswordResetMail(user);
                    return new ResponseEntity<>("e-mail was sent", HttpStatus.OK);
                }).orElse(new ResponseEntity<>("e-mail address not registered", HttpStatus.BAD_REQUEST));
    }

    /**
     * POST   /account/reset_password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @return the ResponseEntity with status 200 (OK) if the password has been reset,
     * or status 400 (Bad Request) or 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset_password/finish",
            produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    @Override
    public ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private boolean checkPasswordLength(String password) {
        return (!StringUtils.isEmpty(password) &&
                password.length() >= CorManagedUserDTO.PASSWORD_MIN_LENGTH &&
                password.length() <= CorManagedUserDTO.PASSWORD_MAX_LENGTH);
    }


}
