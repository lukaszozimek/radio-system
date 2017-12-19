package io.protone.application.web.api.library.impl;


import io.protone.application.web.api.library.LibraryFileResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.core.service.CorNetworkService;
import io.protone.library.api.dto.LibFileLibraryDTO;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.mapper.LibFileLibraryMapper;
import io.protone.library.service.LibFileLibraryService;
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
public class LibraryFileResourceImpl implements LibraryFileResource {

    private final Logger log = LoggerFactory.getLogger(LibraryFileResourceImpl.class);

    @Inject
    private LibFileLibraryMapper libFileLibraryMapper;

    @Inject
    private LibFileLibraryService libFileLibraryService;

    @Inject
    private CorNetworkService corNetworkService;


    @Override
    public ResponseEntity<LibFileLibraryDTO> updateLibraryUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                   @ApiParam(value = "libraryDTO", required = true)
                                                                   @Valid @RequestBody LibFileLibraryDTO libraryDTO) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException {

        log.debug("REST request to update library: {}", libraryDTO);
        if (libraryDTO.getId() == null) {
            return createLibraryUsingPOST(organizationShortcut, libraryDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        LibFileLibrary entity = libFileLibraryMapper.DTO2DB(libraryDTO, corNetwork);
        LibFileLibrary resultDB = libFileLibraryService.createOrUpdateLibrary(entity);
        LibFileLibraryDTO libraryDAO = libFileLibraryMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
                .body(libraryDAO);
    }


    @Override
    public ResponseEntity<List<LibFileLibraryDTO>> getAllLibrariesUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                           @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all LibMediaLibraryDTO");
        Slice<LibFileLibrary> libraries = libFileLibraryService.findLibraries(organizationShortcut, pagable);
        return ResponseEntity.ok().headers(PaginationUtil.generateSliceHttpHeaders(libraries))
                .body(libFileLibraryMapper.DBs2DTOs(libraries.getContent()));
    }

    @Override
    public ResponseEntity<LibFileLibraryDTO> createLibraryUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                    @ApiParam(value = "libraryDTO", required = true) @Valid @RequestBody LibFileLibraryDTO libraryDTO
    ) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException {

        log.debug("REST request to create library: {}", libraryDTO);
        if (libraryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("libLibrary", "idexists", "A new libLibrary cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        LibFileLibrary entity = libFileLibraryMapper.DTO2DB(libraryDTO, corNetwork);
        LibFileLibrary resultDB = libFileLibraryService.createOrUpdateLibrary(entity);
        LibFileLibraryDTO libraryDAO = libFileLibraryMapper.DB2DTO(resultDB);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/library/file/" + libraryDAO.getShortcut()))
                .body(libraryDAO);
    }


    @Override
    public ResponseEntity<Void> deleteLibraryUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                         @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        log.debug("REST request to delete LIBLibrary : {}", libraryPrefix);
        libFileLibraryService.deleteLibrary(libraryPrefix, organizationShortcut);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<LibFileLibraryDTO> getLibraryUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {

        log.debug("REST request to get library: {}", libraryPrefix);
        LibFileLibrary library = libFileLibraryService.findLibrary(organizationShortcut, libraryPrefix);
        LibFileLibraryDTO dto = libFileLibraryMapper.DB2DTO(library);
        return Optional.ofNullable(dto)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<LibFileLibraryDTO>> getAllLibrariesForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                     @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all LibMediaLibraryDTO");
        Slice<LibFileLibrary> libraries = libFileLibraryService.findLibrariesByChannel(organizationShortcut, channelShortcut, pagable);
        return ResponseEntity.ok().headers(PaginationUtil.generateSliceHttpHeaders(libraries))
                .body(libFileLibraryMapper.DBs2DTOs(libraries.getContent()));
    }


    @Override
    public ResponseEntity<LibFileLibraryDTO> updateLibraryForChannelUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "library", required = true) @Valid @RequestBody LibFileLibraryDTO libraryDTO) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException {
        log.debug("REST request to update library: {}", libraryDTO);

        if (libraryDTO.getId() == null) {
            return createLibraryForChannelUsingPOST(organizationShortcut, channelShortcut, libraryDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        LibFileLibrary entity = libFileLibraryMapper.DTO2DB(libraryDTO, corNetwork);
        LibFileLibrary resultDB = libFileLibraryService.createOrUpdateLibrary(entity);
        LibFileLibraryDTO libraryDAO = libFileLibraryMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
                .body(libraryDAO);
    }

    @Override
    public ResponseEntity<LibFileLibraryDTO> createLibraryForChannelUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "library", required = true) @Valid @RequestBody LibFileLibraryDTO libraryDTO) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException {
        log.debug("REST request to create library: {}", libraryDTO);
        if (libraryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("libLibrary", "idexists", "A new libLibrary cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        LibFileLibrary entity = libFileLibraryMapper.DTO2DB(libraryDTO, corNetwork);
        LibFileLibrary resultDB = libFileLibraryService.createOrUpdateLibrary(entity);
        LibFileLibraryDTO libraryDAO = libFileLibraryMapper.DB2DTO(resultDB);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/channel/" + channelShortcut + "/library/" + libraryDAO.getShortcut()))
                .body(libraryDAO);
    }


    @Override
    public ResponseEntity<Void> deleteLibraryForChannelUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                   @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        log.debug("REST request to delete LIBLibrary : {}", libraryPrefix);
        libFileLibraryService.deleteLibrary(libraryPrefix, channelShortcut, organizationShortcut);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<LibFileLibraryDTO> getLibraryForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        log.debug("REST request to get library: {}", libraryPrefix);
        LibFileLibrary library = libFileLibraryService.findLibraryByChannel(organizationShortcut, channelShortcut, libraryPrefix);
        LibFileLibraryDTO dto = libFileLibraryMapper.DB2DTO(library);
        return Optional.ofNullable(dto)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
