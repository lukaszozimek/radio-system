package io.protone.custom.web.rest.network.library;

import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.LibResponseEntity;
import io.protone.custom.service.dto.SchBlockPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiNetworkLibraryBlock {

    @ApiOperation(value = "getRandomBlockFromSelectedLibrary", notes = "", response = LibResponseEntity.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibResponseEntity.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibResponseEntity.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibResponseEntity.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibResponseEntity.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library/{libraryPrefix}/randomBlock",
        method = RequestMethod.GET)
    ResponseEntity<SchBlockPT> getRandomBlockFromSelectedLibrary(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                           @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                           @ApiParam(value = "size", required = false) @RequestParam(value = "size", required = false) Integer size,
                                           @ApiParam(value = "maxLength", required = false) @RequestParam(value = "maxLength", required = false) Integer maxLength);

}
