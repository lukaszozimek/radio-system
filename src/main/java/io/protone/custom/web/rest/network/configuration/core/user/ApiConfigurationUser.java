package io.protone.custom.web.rest.network.configuration.core.user;

import io.protone.custom.service.dto.CoreKeyPT;
import io.protone.custom.service.dto.CoreUserPT;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by lukaszozimek on 27/02/2017.
 */
@Api(value = "custom", description = "the api API")
public interface ApiConfigurationUser {
    ResponseEntity createUser(@RequestBody CoreUserPT managedUserVM) throws URISyntaxException;

    ResponseEntity<CoreUserPT> updateUser(@RequestBody CoreUserPT coreUserPT);

    ResponseEntity<List<CoreUserPT>> getAllUsers(@ApiParam Pageable pageable)
        throws URISyntaxException;

    ResponseEntity<CoreUserPT> getUser(@PathVariable String login);

    ResponseEntity<Void> deleteUser(@PathVariable String login);


}
