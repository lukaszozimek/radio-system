package io.protone.custom.web.rest.network.configuration.core.user;

import io.protone.web.rest.dto.cor.CorUserDTO;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface CorUserConfigurationResource {

    @ApiOperation(value = "getAllUsers", notes = "", response = CorUserDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorUserDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorUserDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorUserDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorUserDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/user",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CorUserDTO>> getAllUsersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getUser", notes = "", response = CorUserDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorUserDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorUserDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorUserDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorUserDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/user/{login}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CorUserDTO> getUserUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                               @ApiParam(value = "login", required = true) @PathVariable("login") String login);


    @ApiOperation(value = "updateUser", notes = "", response = CorUserDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorUserDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorUserDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorUserDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorUserDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorUserDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/user",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CorUserDTO> updateUserUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "contactStatusPT", required = true) @Valid @RequestBody CorUserDTO corUserDTO) throws URISyntaxException;


    @ApiOperation(value = "createUser", notes = "", response = CorUserDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorUserDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorUserDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorUserDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorUserDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorUserDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/user",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CorUserDTO> createUserUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "contactStatusPT", required = true) @Valid @RequestBody CorUserDTO corUserDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteUser", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/user/{login}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteUserUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                               @ApiParam(value = "login", required = true) @PathVariable("login") String login);

}
