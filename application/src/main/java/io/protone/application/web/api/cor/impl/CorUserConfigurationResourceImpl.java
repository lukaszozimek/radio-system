package io.protone.application.web.api.cor.impl;

/**
 * Created by lukaszozimek on 27/02/2017.
 */

import io.github.jhipster.web.util.ResponseUtil;
import io.protone.core.repository.CorUserRepository;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorUser;
import io.protone.service.cor.CorMailService;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.cor.CorUserService;
import io.protone.web.api.cor.CorUserConfigurationResource;
import io.protone.web.rest.dto.cor.CorUserDTO;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing users.
 * <p>
 * <p>This class accesses the User entity, and needs to fetch its collection of authorities.</p>
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * </p>
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>Another option would be to have a specific JPA entity graph to handle this case.</p>
 */
@RestController
public class CorUserConfigurationResourceImpl implements CorUserConfigurationResource {

    private static final String ENTITY_NAME = "userManagement";
    private final Logger log = LoggerFactory.getLogger(CorUserConfigurationResourceImpl.class);
    private final CorUserRepository userRepository;

    private final CorMailService mailService;

    private final CorUserService userService;

    private final CorNetworkService corNetworkService;

    public CorUserConfigurationResourceImpl(CorUserRepository userRepository, CorMailService mailService,
                                            CorUserService userService, CorNetworkService corNetworkService) {

        this.userRepository = userRepository;
        this.mailService = mailService;
        this.userService = userService;
        this.corNetworkService = corNetworkService;
    }

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     * </p>
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */


    @Override
    public ResponseEntity createUserUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                              @ApiParam(value = "corUserDTO", required = true) @Valid @RequestBody CorUserDTO corUserDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact User : {}", corUserDTO);

        if (corUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "createExistingUser", "")).body(null);
        }
        //Lowercase the user login before comparing with database
        if (userRepository.findOneByLogin(corUserDTO.getLogin().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use"))
                .body(null);
        } else if (userRepository.findOneByEmail(corUserDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use"))
                .body(null);
        } else {
            CorUser newUser = userService.createUser(corUserDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert("userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT  /users : Updates an existing User.
     *
     * @param corUserDTO the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user,
     * or with status 400 (Bad Request) if the login or email is already in use,
     * or with status 500 (Internal Server Error) if the user couldn't be updated
     */


    @Override
    public ResponseEntity<CorUserDTO> updateUserUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "corUserDTO", required = true) @Valid @RequestBody CorUserDTO corUserDTO) throws URISyntaxException {
        if (corUserDTO.getId() == null) {
            return createUserUsingPOST(networkShortcut, corUserDTO);
        }
        log.debug("REST request to update User : {}", corUserDTO);
        Optional<CorUser> existingUser = userRepository.findOneByEmail(corUserDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(corUserDTO.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "E-mail already in use")).body(null);
        }
        existingUser = userRepository.findOneByLogin(corUserDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(corUserDTO.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use")).body(null);
        }
        Optional<CorUserDTO> updatedUser = Optional.of(userService.updateUser(corUserDTO));

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("userManagement.updated", corUserDTO.getLogin()));
    }


    @Override
    public ResponseEntity<List<CorUserDTO>> getAllUsersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        CorNetwork network = corNetworkService.findNetwork(networkShortcut);
        List<CorUserDTO> corUserDTOList = userService.getAllManagedUsers(network);
        return ResponseEntity.ok().body(corUserDTOList);
    }

    /**
     * GET  /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @Override
    public ResponseEntity<CorUserDTO> getUserUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "login", required = true) @PathVariable("login") String login) {
        log.debug("REST request to get User : {}", login);
        CorNetwork network = corNetworkService.findNetwork(networkShortcut);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLoginAndNetwork(login, network)
                .map(CorUserDTO::new));
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Override
    public ResponseEntity<Void> deleteUserUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "login", required = true) @PathVariable("login") String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("userManagement.deleted", login)).build();
    }

}
