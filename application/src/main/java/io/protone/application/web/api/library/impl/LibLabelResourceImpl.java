package io.protone.application.web.api.library.impl;


import io.protone.application.web.api.library.LibLabelResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.library.api.dto.LibLabelDTO;
import io.protone.library.domain.LibLabel;
import io.protone.library.mapper.LibLabelMapper;
import io.protone.library.service.LibLabelService;
import io.swagger.annotations.ApiParam;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class LibLabelResourceImpl implements LibLabelResource {
    private final Logger log = LoggerFactory.getLogger(LibLabelResourceImpl.class);

    @Inject
    private LibLabelService libLabelService;

    @Inject
    private LibLabelMapper libLabelMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<LibLabelDTO> updateLabelUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                           @ApiParam(value = "libLabelDTO", required = true) @Valid @RequestBody LibLabelDTO libLabelDTO) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to update label: {}", libLabelDTO);
        if (libLabelDTO.getId() == null) {
            return createLabelUsingPOST(organizationShortcut, libLabelDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        LibLabel entity = libLabelMapper.DTO2DB(libLabelDTO, corNetwork);
        LibLabel resultDB = libLabelService.createOrUpdateLabel(entity);
        LibLabelDTO labelDAO = libLabelMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
                .body(labelDAO);
    }


    @Override
    public ResponseEntity<List<LibLabelDTO>> getAllLabelsUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                  @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all LibLabelDTO");
        Slice<LibLabel> libraries = libLabelService.findLabels(organizationShortcut, pagable);
        return ResponseEntity.ok().headers(PaginationUtil.generateSliceHttpHeaders(libraries))
                .body(libLabelMapper.DBs2DTOs(libraries.getContent()));
    }


    @Override
    public ResponseEntity<LibLabelDTO> createLabelUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                            @ApiParam(value = "labelDTO", required = true) @Valid @RequestBody LibLabelDTO labelDTO) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to create label: {}", labelDTO);
        if (labelDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("libLabel", "idexists", "A new libLabel cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        LibLabel entity = libLabelMapper.DTO2DB(labelDTO, corNetwork);
        LibLabel resultDB = libLabelService.createOrUpdateLabel(entity);
        LibLabelDTO labelDAO = libLabelMapper.DB2DTO(resultDB);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/label/label/" + labelDAO.getId()))
                .body(labelDAO);
    }

    @Override
    public ResponseEntity<Void> deleteLabelUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete LIBLabel : {}", id);
        libLabelService.deleteLabel(id, organizationShortcut);
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<LibLabelDTO> getLabelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get label: {}", id);
        LibLabel label = libLabelService.findLabel(organizationShortcut, id);
        LibLabelDTO dto = libLabelMapper.DB2DTO(label);
        return Optional.ofNullable(dto)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
