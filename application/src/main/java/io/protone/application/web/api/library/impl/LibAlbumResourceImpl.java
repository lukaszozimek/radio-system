package io.protone.application.web.api.album.impl;


import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;

import io.protone.library.api.dto.LibAlbumDTO;
import io.protone.library.domain.LibAlbum;
import io.protone.library.mapper.LibAlbumMapper;
import io.protone.library.service.LibAlbumService;
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
public class LibAlbumResourceImpl {
    private final Logger log = LoggerFactory.getLogger(LibAlbumResourceImpl.class);

    @Inject
    private LibAlbumService libAlbumService;

    @Inject
    private LibAlbumMapper libAlbumMapper;

    @Inject
    private CorNetworkService corNetworkService;

    public ResponseEntity<LibAlbumDTO> updateAlbumWithOutCoverUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "libAlbumDTO", required = true) @Valid @RequestBody LibAlbumDTO libAlbumDTO) throws URISyntaxException, TikaException, IOException, SAXException {

        log.debug("REST request to update album: {}", libAlbumDTO);
        if (libAlbumDTO.getId() == null) {
            return createAlbumUsingPOST(networkShortcut, libAlbumDTO, null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibAlbum entity = libAlbumMapper.DTO2DB(libAlbumDTO, corNetwork);
        LibAlbum resultDB = libAlbumService.saveOrUpdate(entity);
        LibAlbumDTO albumDAO = libAlbumMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
                .body(albumDAO);
    }


    public ResponseEntity<LibAlbumDTO> updateAlbumWithCoverUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "id", required = true) @PathVariable("id") Long id,
                                                                     @ApiParam(value = "albumDTO", required = true) @Valid @RequestPart("albumDTO") LibAlbumDTO albumDTO,
                                                                     @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover

    ) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to update album: {}", albumDTO);

        if (albumDTO.getId() == null) {
            return createAlbumUsingPOST(networkShortcut, albumDTO, cover);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibAlbum entity = libAlbumMapper.DTO2DB(albumDTO, corNetwork);
        LibAlbum resultDB = libAlbumService.save(entity, cover, null);
        LibAlbumDTO albumDAO = libAlbumMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
                .body(albumDAO);
    }


    public ResponseEntity<List<LibAlbumDTO>> getAllAlbumsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "pagable", required = true) Pageable pagable) {

        log.debug("REST request to get all LibAlbumDTO");
        Slice<LibAlbum> libraries = libAlbumService.findAlbums(networkShortcut, pagable);
        return ResponseEntity.ok().headers(PaginationUtil.generateSliceHttpHeaders(libraries))
                .body(libAlbumMapper.DBs2DTOs(libraries.getContent()));
    }


    public ResponseEntity<LibAlbumDTO> createAlbumUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "libAlbumDTO", required = true) @Valid @RequestPart("libAlbumDTO") LibAlbumDTO libAlbumDTO,
                                                            @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover
    ) throws URISyntaxException, TikaException, IOException, SAXException {

        log.debug("REST request to create album: {}", libAlbumDTO);
        if (libAlbumDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("Album", "idexists", "A new Album cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibAlbum entity = libAlbumMapper.DTO2DB(libAlbumDTO, corNetwork);
        LibAlbum resultDB = libAlbumService.save(entity, cover, null);
        LibAlbumDTO albumDAO = libAlbumMapper.DB2DTO(resultDB);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/library/album/" + albumDAO.getId()))
                .body(albumDAO);
    }


    public ResponseEntity<Void> deleteAlbumUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {

        log.debug("REST request to delete Album : {}", id);
        libAlbumService.deleteAlbum(id, networkShortcut);
        return ResponseEntity.ok().build();
    }


    public ResponseEntity<LibAlbumDTO> getAlbumUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get album: {}", id);
        LibAlbum album = libAlbumService.findAlbum(networkShortcut, id);
        LibAlbumDTO dto = libAlbumMapper.DB2DTO(album);
        return Optional.ofNullable(dto)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
