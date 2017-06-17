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
public interface CrmOpportunityTaskResource {

    @ApiOperation(value = "getAllOpportunityActivities", notes = "", response = CrmTaskDTO.class, responseContainer = "List", tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmTaskDTO>> getAllOpportunityActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                         @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "updateOpportunityActivity", notes = "", response = CrmTaskDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmTaskDTO> updateOpportunityActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                 @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException;


    @ApiOperation(value = "createOpportunityActivity", notes = "", response = CrmTaskDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmTaskDTO> createOpportunityActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                  @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException;

    @ApiOperation(value = "deleteOpportunityActivity", notes = "", response = Void.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteOpportunityActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getOpportunityActivity", notes = "", response = CrmTaskDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmTaskDTO> getOpportunityActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getOpportunityTaskComments", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task/{taskId}/comment",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmTaskCommentDTO>> getOpportunityTaskCommentsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                        @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id,
                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "createOpportunityActivtyComment", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task/{taskId}/comment",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmTaskCommentDTO> createOpportunityActivtyCommentUsigPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                       @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                       @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException;

    @ApiOperation(value = "editOpportunityActivtyComment", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task/{taskId}/comment",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmTaskCommentDTO> editOpportunityActivtyCommentUsigPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                    @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                    @ApiParam(value = "taskCommentDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException;

    @ApiOperation(value = "getOpportunityTaskComment", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task/{taskId}/comment/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmTaskCommentDTO> getOpportunityTaskCommentUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                 @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "deleteOpportunityTaskComment", notes = "", response = CrmTaskCommentDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskCommentDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskCommentDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task/{taskId}/comment/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteOpportunityTaskCommentUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                          @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
