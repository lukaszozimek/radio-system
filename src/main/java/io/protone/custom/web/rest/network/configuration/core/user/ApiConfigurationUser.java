package io.protone.custom.web.rest.network.configuration.core.user;

import io.protone.custom.service.dto.CoreUserPT;
import io.swagger.annotations.*;
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

    @ApiOperation(value = "getAllUsers", notes = "", response = CoreUserPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreUserPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreUserPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreUserPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreUserPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/user",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CoreUserPT>> getAllUsersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getUser", notes = "", response = CoreUserPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreUserPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreUserPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreUserPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreUserPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/user/{login}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CoreUserPT> getUserUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "login", required = true) @PathVariable("login") String login);


    @ApiOperation(value = "updateUser", notes = "", response = CoreUserPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreUserPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreUserPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreUserPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreUserPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreUserPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/user",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CoreUserPT> updateUserUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "contactStatusPT", required = true) @RequestBody CoreUserPT coreUserPT);


    @ApiOperation(value = "createUser", notes = "", response = CoreUserPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreUserPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreUserPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreUserPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreUserPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreUserPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/user",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CoreUserPT> createUserUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "contactStatusPT", required = true) @RequestBody CoreUserPT coreUserPT) throws URISyntaxException;


    @ApiOperation(value = "deleteUser", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/user/{login}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteUserUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "login", required = true) @PathVariable("login") String login);

}
