package io.protone.custom.web.rest.network;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CoreUserPT;
import io.protone.service.dto.UserDTO;
import io.protone.web.rest.vm.KeyAndPasswordVM;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by lukaszozimek on 27/02/2017.
 */
@Api(value = "custom", description = "the api API")
public interface ApiUser {
    ResponseEntity registerAccount(@Valid @RequestBody CoreManagedUserPT managedUserVM);

    ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key);

    String isAuthenticated(HttpServletRequest request);

    ResponseEntity<CoreUserPT> getAccount();

    ResponseEntity<String> saveAccount(@Valid @RequestBody UserDTO userDTO);

    ResponseEntity changePassword(@RequestBody String password);

    ResponseEntity requestPasswordReset(@RequestBody String mail);

    ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword);


}
