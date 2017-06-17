package io.protone.web.api.crm;

import io.protone.web.rest.dto.crm.CrmTaskDTO;
import io.protone.web.rest.dto.traffic.CrmTaskCommentDTO;
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
public interface CrmLeadTaskResource {


    @ApiOperation(value = "getAllLeadActivities", notes = "", response = CrmTaskDTO.class, responseContainer = "List", tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmTaskDTO>> getAllLeadActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                  @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "updateLeadActivity", notes = "", response = CrmTaskDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmTaskDTO> updateLeadActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                          @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException;


    @ApiOperation(value = "createLeadActivity", notes = "", response = CrmTaskDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmTaskDTO> createLeadActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                           @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteLeadActivity", notes = "", response = Void.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLeadActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getLeadId", notes = "", response = CrmTaskDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmTaskDTO> getLeadActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "getLeadTaskComments", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task/{taskId}/comment",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmTaskCommentDTO>> getLeadTaskCommentsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                        @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "createLeadActivtyComment", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task/{taskId}/comment",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmTaskCommentDTO> createLeadActivtyCommentUsigPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                       @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                       @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException;

    @ApiOperation(value = "editLeadActivtyComment", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task/{taskId}/comment",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmTaskCommentDTO> editLeadActivtyCommentUsigPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                    @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                    @ApiParam(value = "taskCommentDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException;

    @ApiOperation(value = "getLeadTaskComment", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task/{taskId}/comment/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmTaskCommentDTO> getLeadTaskCommentUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                 @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "deleteLeadTaskComment", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task/{taskId}/comment/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLeadTaskCommentUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                          @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
