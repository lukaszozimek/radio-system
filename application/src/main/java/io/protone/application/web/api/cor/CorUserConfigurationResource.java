package io.protone.application.web.api.cor;


import io.protone.core.api.dto.CorUserDTO;
import io.swagger.annotations.*;
import org.apache.tika.exception.TikaException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.validation.Valid;
import java.io.IOException;
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
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/user",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<CorUserDTO>> getAllUsersUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                         @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getUser", notes = "", response = CorUserDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CorUserDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CorUserDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CorUserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CorUserDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/user/{login}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<CorUserDTO> getUserUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                               @ApiParam(value = "login", required = true) @PathVariable("login") String login);


    @ApiOperation(value = "updateUser", notes = "", response = CorUserDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CorUserDTO.class),
            @ApiResponse(code = 201, message = "Created", response = CorUserDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CorUserDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CorUserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CorUserDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/user",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<CorUserDTO> updateUserUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                  @ApiParam(value = "contactStatusPT", required = true) @Valid @RequestBody CorUserDTO corUserDTO) throws URISyntaxException, TikaException, IOException, SAXException;

    @ApiOperation(value = "updateUserWithAvatar", notes = "", response = CorUserDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CorUserDTO.class),
            @ApiResponse(code = 201, message = "Created", response = CorUserDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CorUserDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CorUserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CorUserDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/user/{login}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CorUserDTO> updateUserWithAvatarUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                             @ApiParam(value = "login", required = true) @PathVariable("login") String login,
                                                             @ApiParam(value = "corUserDTO", required = true) @Valid @RequestPart("corUserDTO") CorUserDTO corUserDTO,
                                                             @ApiParam(value = "avatar", required = true) @RequestPart("avatar") MultipartFile logo) throws URISyntaxException, TikaException, IOException, SAXException;

    @ApiOperation(value = "createUser", notes = "", response = CorUserDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CorUserDTO.class),
            @ApiResponse(code = 201, message = "Created", response = CorUserDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CorUserDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CorUserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CorUserDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/user",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CorUserDTO> createUserUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                   @ApiParam(value = "corUserDTO", required = true) @Valid @RequestPart("corUserDTO") CorUserDTO corUserDTO,
                                                   @ApiParam(value = "avatar", required = true) @RequestPart("avatar") MultipartFile logo) throws URISyntaxException, TikaException, IOException, SAXException;


    @ApiOperation(value = "deleteUser", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/user/{login}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteUserUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                               @ApiParam(value = "login", required = true) @PathVariable("login") String login);

}
