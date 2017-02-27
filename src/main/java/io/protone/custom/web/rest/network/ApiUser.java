package io.protone.custom.web.rest.network;

import io.protone.custom.service.dto.CoreNetworkPT;
import io.protone.service.dto.UserDTO;
import io.protone.web.rest.vm.KeyAndPasswordVM;
import io.protone.web.rest.vm.ManagedUserVM;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by lukaszozimek on 27/02/2017.
 */
@Api(value = "custom", description = "the api API")
public interface ApiUser {

    @ApiOperation(value = "createNetwork", notes = "", response = CoreNetworkPT.class, tags = {"NETWORK", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class)})
    @RequestMapping(value = "/api/register",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CoreNetworkPT> registerAccountUsingPOST(@ApiParam(value = "network", required = true) @Valid @RequestBody ManagedUserVM managedUserVM);


    @ApiOperation(value = "getNetwork", notes = "", response = CoreNetworkPT.class, tags = {"NETWORK", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class)})
    @RequestMapping(value = "/api/activate",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CoreNetworkPT> activateAccountUsingGET(@ApiParam(value = "networkShortcut", required = true) @RequestParam(value = "key") String key);

    @ApiOperation(value = "getNetwork", notes = "", response = CoreNetworkPT.class, tags = {"NETWORK", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class)})
    @RequestMapping(value = "/api/authenticate",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CoreNetworkPT> authenticateUsingGET(HttpServletRequest request);

    @ApiOperation(value = "getNetwork", notes = "", response = CoreNetworkPT.class, tags = {"NETWORK", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class)})
    @RequestMapping(value = "/api/account",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CoreNetworkPT> getAccountUsingGET(HttpServletRequest request);

    @ApiOperation(value = "createNetwork", notes = "", response = CoreNetworkPT.class, tags = {"NETWORK", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class)})
    @RequestMapping(value = "/api/account/reset_password/init",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CoreNetworkPT> saveAccountUsingPOST(@ApiParam(value = "network", required = true) @Valid @RequestBody UserDTO userDTO);

    @ApiOperation(value = "createNetwork", notes = "", response = CoreNetworkPT.class, tags = {"NETWORK", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class)})
    @RequestMapping(value = "/api/account/change_password",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CoreNetworkPT> changePasswordUsingPOST(@ApiParam(value = "network", required = true) @RequestBody String password);



    @ApiOperation(value = "createNetwork", notes = "", response = CoreNetworkPT.class, tags = {"NETWORK", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class)})
    @RequestMapping(value = "/api/account/reset_password/finish",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CoreNetworkPT> requestPasswordResetUsingPOST(@ApiParam(value = "network", required = true)@RequestBody KeyAndPasswordVM keyAndPassword);


}
