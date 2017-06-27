package io.protone.web.api.cor;

import io.protone.web.rest.dto.cor.CorManagedUserDTO;
import io.protone.web.rest.dto.cor.CorUserDTO;
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
@Api(value = "protone", description = "Protone backend API documentation")
public interface CorUserResource {
    ResponseEntity registerAccount(@Valid @RequestBody CorManagedUserDTO managedUserVM);

    ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key);

    String isAuthenticated(HttpServletRequest request);

    ResponseEntity<CorUserDTO> getAccount();

    ResponseEntity<String> saveAccount(@Valid @RequestBody CorUserDTO userDTO);

    ResponseEntity changePassword(@RequestBody String password);

    ResponseEntity requestPasswordReset(@RequestBody String mail);

    ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword);


}
