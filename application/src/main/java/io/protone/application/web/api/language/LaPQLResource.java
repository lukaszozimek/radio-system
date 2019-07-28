package io.protone.application.web.api.language;

import io.protone.core.api.dto.thin.CorFilterThinDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by lukaszozimek on 13/07/2017.
 */
@Api(value = "protone", description = "Protone backend API documentation")
public interface LaPQLResource {

    @ApiOperation(value = "updateContactWithoutAvatar", notes = "", response = Void.class, tags = {"LANGUAGE",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 201, message = "Created", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/language/pql/filter",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    List queryElements(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                       @ApiParam(value = "corFilterDTO", required = true) @RequestBody @Valid CorFilterThinDTO corFilterDTO) throws IOException;
}
