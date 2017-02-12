package io.protone.custom.web.rest.network.traffic;

import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.service.dto.TraCustomerAdvertismentsPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiNetworkTrafficCustomerAdvertisement {


    @ApiOperation(value = "getAllCustomersAdvertismentsUsingGET", notes = "", response = TraCustomerAdvertismentsPT.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerAdvertismentsPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerAdvertismentsPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerAdvertismentsPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerAdvertismentsPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/customer/{customerShortcut}/advertisement",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraAdvertisementPT>> getAllCustomersAdvertismentsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut);


}
