package io.protone.application.web.api.library.impl;


import io.protone.application.web.api.library.LibArtistResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.core.service.CorNetworkService;
import io.protone.library.api.dto.LibArtistDTO;
import io.protone.library.api.dto.LibArtistDTO;
import io.protone.library.domain.LibArtist;
import io.protone.library.mapper.LibAlbumMapper;
import io.protone.library.mapper.LibArtistMapper;
import io.protone.library.service.LibAlbumService;
import io.protone.library.service.LibArtistService;
import io.swagger.annotations.*;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
public class LibArtistResourceImpl implements LibArtistResource {
    private final Logger log = LoggerFactory.getLogger(LibArtistResourceImpl.class);


    @Inject
    private LibArtistService libArtistService;

    @Inject
    private LibArtistMapper libArtistMapper;

    @Inject
    private CorNetworkService corNetworkService;

    public ResponseEntity<LibArtistDTO> updateArtistWithOutImageUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "libArtistDTO", required = true) @Valid @RequestBody LibArtistDTO libArtistDTO) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to update library: {}", libArtistDTO);
        if (libArtistDTO.getId() == null) {
            return createArtistUsingPOST(networkShortcut, libArtistDTO, null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibArtist entity = libArtistMapper.DTO2DB(libArtistDTO, corNetwork);
        LibArtist resultDB = libArtistService.createOrUpdateArtist(entity);
        LibArtistDTO libraryDAO = libArtistMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
                .body(libraryDAO);
    }


    public ResponseEntity<LibArtistDTO> updateArtistWithImageUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id,
                                                                       @ApiParam(value = "libArtistDTO", required = true) @Valid @RequestPart("libArtistDTO") LibArtistDTO libArtistDTO,
                                                                       @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to update library: {}", libArtistDTO);

        if (libArtistDTO.getId() == null) {
            return createArtistUsingPOST(networkShortcut, libArtistDTO, cover);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibArtist entity = libArtistMapper.DTO2DB(libArtistDTO, corNetwork);
        LibArtist resultDB = libArtistService.createOrUpdateArtistWithImage(entity, cover);
        LibArtistDTO libraryDAO = libArtistMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
                .body(libraryDAO);
    }


    public ResponseEntity<List<LibArtistDTO>> getAllArtistsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all LibArtistDTO");
        Slice<LibArtist> libraries = libArtistService.findArtists(networkShortcut, pagable);
        return ResponseEntity.ok().headers(PaginationUtil.generateSliceHttpHeaders(libraries))
                .body(libArtistMapper.DBs2DTOs(libraries.getContent()));
    }

    public ResponseEntity<LibArtistDTO> createArtistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "libArtistDTO", required = true) @Valid @RequestPart("libArtistDTO") LibArtistDTO libArtistDTO,
                                                              @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to create library: {}", libArtistDTO);
        if (libArtistDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("libArtist", "idexists", "A new libArtist cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibArtist entity = libArtistMapper.DTO2DB(libArtistDTO, corNetwork);
        LibArtist resultDB = libArtistService.createOrUpdateArtistWithImage(entity, cover);
        LibArtistDTO libraryDAO = libArtistMapper.DB2DTO(resultDB);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/library/artist/" + libraryDAO.getId()))
                .body(libraryDAO);
    }


    public ResponseEntity<Void> deleteArtistUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete LIBArtist : {}", id);
        libArtistService.deleteArtist(id, networkShortcut);
        return ResponseEntity.ok().build();
    }


    public ResponseEntity<LibArtistDTO> getArtistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get library: {}", id);
        LibArtist library = libArtistService.findArtist(networkShortcut, id);
        LibArtistDTO dto = libArtistMapper.DB2DTO(library);
        return Optional.ofNullable(dto)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
