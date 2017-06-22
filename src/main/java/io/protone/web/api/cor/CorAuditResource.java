package io.protone.web.api.cor;

import io.github.jhipster.web.util.ResponseUtil;
import io.protone.service.AuditEventService;
import io.protone.web.rest.util.PaginationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for getting the audit events.
 */

@Api(value = "protone", description = "Protone backend API documentation")
public interface CorAuditResource {


    public ResponseEntity<List<AuditEvent>> getAll(@ApiParam Pageable pageable) throws URISyntaxException;

    public ResponseEntity<List<AuditEvent>> getByDates(
        @RequestParam(value = "fromDate") LocalDate fromDate,
        @RequestParam(value = "toDate") LocalDate toDate,
        @ApiParam Pageable pageable) throws URISyntaxException;

    public ResponseEntity<AuditEvent> get(@PathVariable Long id);
}
