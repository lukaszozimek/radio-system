package io.protone.application.web.api.crm;


import io.protone.crm.api.dto.CrmTaskCommentDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface CrmContactTaskCommentResource {

    @ApiOperation(value = "getContactTaskComments", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{taskId}/comment",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmTaskCommentDTO>> getContactTaskCommentsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                           @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                           @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "createContactActivtyComment", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{taskId}/comment",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmTaskCommentDTO> createContactActivtyCommentUsigPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                          @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                          @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException;

    @ApiOperation(value = "editContactActivtyComment", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{taskId}/comment",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmTaskCommentDTO> editContactActivtyCommentUsigPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                       @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                       @ApiParam(value = "taskCommentDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException;

    @ApiOperation(value = "getContactTaskComment", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{taskId}/comment/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmTaskCommentDTO> getContactTaskCommentUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                    @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                    @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "deleteContactTaskComment", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{taskId}/comment/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteContactTaskCommentUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                             @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                             @ApiParam(value = "id", required = true) @PathVariable("id") Long id);
}
