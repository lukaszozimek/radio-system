package io.protone.custom.web.rest.network.configuration.core.user.impl;

/**
 * Created by lukaszozimek on 27/02/2017.
 */

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.protone.config.Constants;
import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.CustomMailService;
import io.protone.custom.service.CustomCorUserService;
import io.protone.custom.service.dto.CoreUserPT;
import io.protone.custom.web.rest.network.configuration.core.user.ApiConfigurationUser;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorUser;
import io.protone.repository.custom.CustomCorUserRepository;
import io.protone.security.AuthoritiesConstants;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
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
public class ApiUserConfigurationImpl implements ApiConfigurationUser {

    private final Logger log = LoggerFactory.getLogger(ApiUserConfigurationImpl.class);

    private static final String ENTITY_NAME = "userManagement";

    private final CustomCorUserRepository userRepository;

    private final CustomMailService mailService;

    private final CustomCorUserService userService;

    private final CorNetworkService corNetworkService;

    public ApiUserConfigurationImpl(CustomCorUserRepository userRepository, CustomMailService mailService,
                                    CustomCorUserService userService, CorNetworkService corNetworkService) {

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
                                              @ApiParam(value = "coreUserPT", required = true) @RequestBody CoreUserPT coreUserPT) throws URISyntaxException {
        log.debug("REST request to save User : {}", coreUserPT);

        //Lowercase the user login before comparing with database
        if (userRepository.findOneByLogin(coreUserPT.getLogin().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use"))
                .body(null);
        } else if (userRepository.findOneByEmail(coreUserPT.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use"))
                .body(null);
        } else {
            CorUser newUser = userService.createUser(coreUserPT);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert("userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT  /users : Updates an existing User.
     *
     * @param coreUserPT the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user,
     * or with status 400 (Bad Request) if the login or email is already in use,
     * or with status 500 (Internal Server Error) if the user couldn't be updated
     */


    @Override
    public ResponseEntity<CoreUserPT> updateUserUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "coreUserPT", required = true) @RequestBody CoreUserPT coreUserPT) {
        log.debug("REST request to update User : {}", coreUserPT);
        Optional<CorUser> existingUser = userRepository.findOneByEmail(coreUserPT.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(coreUserPT.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "E-mail already in use")).body(null);
        }
        existingUser = userRepository.findOneByLogin(coreUserPT.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(coreUserPT.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use")).body(null);
        }
        Optional<CoreUserPT> updatedUser = userService.updateUser(coreUserPT);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("userManagement.updated", coreUserPT.getLogin()));
    }


    @Override
    public ResponseEntity<List<CoreUserPT>> getAllUsersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        CorNetwork network = corNetworkService.findNetwork(networkShortcut);
        List<CoreUserPT> coreUserPTList = userService.getAllManagedUsers(network);
        return ResponseEntity.ok().body(coreUserPTList);
    }

    /**
     * GET  /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @Override
    public ResponseEntity<CoreUserPT> getUserUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "login", required = true) @PathVariable("login") String login) {
        log.debug("REST request to get User : {}", login);
        CorNetwork network = corNetworkService.findNetwork(networkShortcut);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLoginAndNetwork(login, network)
                .map(CoreUserPT::new));
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