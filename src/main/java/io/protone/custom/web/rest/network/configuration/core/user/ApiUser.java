package io.protone.custom.web.rest.network.configuration.core.user;

import io.protone.custom.service.dto.CoreKeyPT;
import io.protone.custom.service.dto.CoreUserPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by lukaszozimek on 27/02/2017.
 */
@Api(value = "custom", description = "the api API")
public interface ApiUser {
    @ApiOperation(value = "getNetworkUsers", notes = "", response = CoreKeyPT.class, tags = {"CONFIGURATION", "USER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreKeyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreKeyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreKeyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreKeyPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/user/{login}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CoreKeyPT> getUserUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                              @ApiParam(value = "login", required = true) @PathVariable String login);


    @ApiOperation(value = "deleteNetworkUser", notes = "", response = Void.class, tags = {"CONFIGURATION", "USER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/user/{login}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteUserUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                               @ApiParam(value = "login", required = true) @PathVariable String login);


    @ApiOperation(value = "getAllNetworkUsers", notes = "", response = CoreKeyPT.class, responseContainer = "List", tags = {"CONFIGURATION", "DICTIONARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreKeyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreKeyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreKeyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreKeyPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/user/",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CoreKeyPT>> getAllUsersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "updateNetworkUser", notes = "", response = CoreKeyPT.class, tags = {"CONFIGURATION", "USER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreKeyPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreKeyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreKeyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreKeyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreKeyPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/user/",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CoreKeyPT> updateUserUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                 @ApiParam(value = "managedUserVM", required = true) @RequestBody CoreUserPT managedUserVM);


    @ApiOperation(value = "createNetworkUser", notes = "", response = CoreKeyPT.class, tags = {"CONFIGURATION", "USER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreKeyPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreKeyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreKeyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreKeyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreKeyPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/user/",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CoreKeyPT> createUserUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "managedUserVM", required = true) @RequestBody CoreUserPT managedUserVM);

}
