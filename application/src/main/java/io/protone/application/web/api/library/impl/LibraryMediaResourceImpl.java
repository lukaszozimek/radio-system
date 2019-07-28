package io.protone.application.web.api.library.impl;


import io.protone.application.web.api.library.LibraryMediaResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.core.service.CorNetworkService;
import io.protone.library.api.dto.LibMediaLibraryDTO;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.mapper.LibLibraryMediaMapper;
import io.protone.library.service.LibLibraryMediaService;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class LibraryMediaResourceImpl implements LibraryMediaResource {

    private final Logger log = LoggerFactory.getLogger(LibraryMediaResourceImpl.class);

    @Inject
    private LibLibraryMediaMapper libLibraryMediaMapper;

    @Inject
    private LibLibraryMediaService libLibraryMediaService;

    @Inject
    private CorNetworkService corNetworkService;


    @Override
    public ResponseEntity<LibMediaLibraryDTO> updateLibraryWithOutCoverUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "library", required = true)
                                                                           @Valid @RequestBody LibMediaLibraryDTO library) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException {
        log.debug("REST request to update library: {}", library);
        if (library.getId() == null) {
            return createLibraryUsingPOST(networkShortcut, library, null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibMediaLibrary entity = libLibraryMediaMapper.DTO2DB(library, corNetwork);
        LibMediaLibrary resultDB = libLibraryMediaService.createOrUpdateLibrary(entity);
        LibMediaLibraryDTO libraryDAO = libLibraryMediaMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
                .body(libraryDAO);
    }

    @Override
    public ResponseEntity<LibMediaLibraryDTO> updateLibraryWithCoverUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                              @ApiParam(value = "libraryDTO", required = true) @Valid @RequestPart("libraryDTO") LibMediaLibraryDTO libraryDTO,
                                                                              @ApiParam(value = "cover", required = true) @RequestPart("cover") MultipartFile cover) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException {
        log.debug("REST request to update library: {}", libraryDTO);

        if (libraryDTO.getId() == null) {
            return createLibraryUsingPOST(networkShortcut, libraryDTO, cover);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibMediaLibrary entity = libLibraryMediaMapper.DTO2DB(libraryDTO, corNetwork);
        LibMediaLibrary resultDB = libLibraryMediaService.createOrUpdateLibraryWithImage(entity, cover);
        LibMediaLibraryDTO libraryDAO = libLibraryMediaMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
                .body(libraryDAO);
    }

    @Override
    public ResponseEntity<List<LibMediaLibraryDTO>> getAllLibrariesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all LibMediaLibraryDTO");
        Slice<LibMediaLibrary> libraries = libLibraryMediaService.findLibraries(networkShortcut, pagable);
        return ResponseEntity.ok().headers(PaginationUtil.generateSliceHttpHeaders(libraries))
                .body(libLibraryMediaMapper.DBs2DTOs(libraries.getContent()));
    }

    @Override
    public ResponseEntity<LibMediaLibraryDTO> createLibraryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "libraryDTO", required = true) @Valid @RequestPart("libraryDTO") LibMediaLibraryDTO libraryDTO,
                                                                     @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException {
        log.debug("REST request to create library: {}", libraryDTO);
        if (libraryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("libLibrary", "idexists", "A new libLibrary cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibMediaLibrary entity = libLibraryMediaMapper.DTO2DB(libraryDTO, corNetwork);
        LibMediaLibrary resultDB = libLibraryMediaService.createOrUpdateLibraryWithImage(entity, cover);
        LibMediaLibraryDTO libraryDAO = libLibraryMediaMapper.DB2DTO(resultDB);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/library/" + libraryDAO.getShortcut()))
                .body(libraryDAO);
    }

    @Override
    public ResponseEntity<Void> deleteLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        log.debug("REST request to delete LIBLibrary : {}", libraryPrefix);
        libLibraryMediaService.deleteLibrary(libraryPrefix, networkShortcut);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<LibMediaLibraryDTO> getLibraryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        log.debug("REST request to get library: {}", libraryPrefix);
        LibMediaLibrary library = libLibraryMediaService.findLibrary(networkShortcut, libraryPrefix);
        LibMediaLibraryDTO dto = libLibraryMediaMapper.DB2DTO(library);
        return Optional.ofNullable(dto)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<LibMediaLibraryDTO>> getAllLibrariesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all LibMediaLibraryDTO");
        Slice<LibMediaLibrary> libraries = libLibraryMediaService.findLibrariesByChannel(networkShortcut, channelShortcut, pagable);
        return ResponseEntity.ok().headers(PaginationUtil.generateSliceHttpHeaders(libraries))
                .body(libLibraryMediaMapper.DBs2DTOs(libraries.getContent()));
    }

    @Override
    public ResponseEntity<LibMediaLibraryDTO> updateLibraryWithoutCoverForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                          @ApiParam(value = "library", required = true) @Valid @RequestBody LibMediaLibraryDTO library) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException {
        log.debug("REST request to update library: {}", library);

        if (library.getId() == null) {
            return createLibraryForChannelUsingPOST(networkShortcut, channelShortcut, library, null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibMediaLibrary entity = libLibraryMediaMapper.DTO2DB(library, corNetwork);
        LibMediaLibrary resultDB = libLibraryMediaService.createOrUpdateLibrary(entity);
        LibMediaLibraryDTO libraryDAO = libLibraryMediaMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
                .body(libraryDAO);
    }

    @Override
    public ResponseEntity<LibMediaLibraryDTO> updateLibraryWithCoverForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                        @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                        @ApiParam(value = "library", required = true) @Valid @RequestPart("libraryDTO") LibMediaLibraryDTO libraryDTO,
                                                                                        @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException {
        log.debug("REST request to update library: {}", libraryDTO);

        if (libraryDTO.getId() == null) {
            return createLibraryForChannelUsingPOST(networkShortcut, channelShortcut, libraryDTO, cover);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibMediaLibrary entity = libLibraryMediaMapper.DTO2DB(libraryDTO, corNetwork);
        LibMediaLibrary resultDB = libLibraryMediaService.createOrUpdateLibraryWithImage(entity, cover);
        LibMediaLibraryDTO libraryDAO = libLibraryMediaMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
                .body(libraryDAO);
    }

    @Override
    public ResponseEntity<LibMediaLibraryDTO> createLibraryForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "library", required = true) @Valid @RequestPart("libraryDTO") LibMediaLibraryDTO libraryDTO,
                                                                               @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException {
        log.debug("REST request to create library: {}", libraryDTO);
        if (libraryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("libLibrary", "idexists", "A new libLibrary cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibMediaLibrary entity = libLibraryMediaMapper.DTO2DB(libraryDTO, corNetwork);
        LibMediaLibrary resultDB = libLibraryMediaService.createOrUpdateLibraryWithImage(entity, cover);
        LibMediaLibraryDTO libraryDAO = libLibraryMediaMapper.DB2DTO(resultDB);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/channel/" + channelShortcut + "/library/" + libraryDAO.getShortcut()))
                .body(libraryDAO);
    }

    @Override
    public ResponseEntity<Void> deleteLibraryForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                   @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        log.debug("REST request to delete LIBLibrary : {}", libraryPrefix);
        libLibraryMediaService.deleteLibrary(libraryPrefix, channelShortcut, networkShortcut);
        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<LibMediaLibraryDTO> getLibraryForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        log.debug("REST request to get library: {}", libraryPrefix);
        LibMediaLibrary library = libLibraryMediaService.findLibraryByChannel(networkShortcut, channelShortcut, libraryPrefix);
        LibMediaLibraryDTO dto = libLibraryMediaMapper.DB2DTO(library);
        return Optional.ofNullable(dto)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
