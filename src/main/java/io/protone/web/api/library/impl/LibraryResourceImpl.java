package io.protone.web.api.library.impl;

import io.protone.web.api.library.LibraryResource;
import io.protone.web.rest.dto.library.LibLibraryDTO;
import io.protone.service.library.LibLibraryService;
import io.protone.domain.CorNetwork;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.rest.mapper.LibLibraryMapper;
import io.protone.domain.LibLibrary;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class LibraryResourceImpl implements LibraryResource {

    private final Logger log = LoggerFactory.getLogger(LibraryResourceImpl.class);

    @Inject
    private LibLibraryMapper libLibraryMapper;

    @Inject
    private LibLibraryService libLibraryService;

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<LibLibraryDTO> updateLibraryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "library", required = true) @Valid @RequestBody LibLibraryDTO library) throws URISyntaxException {
        log.debug("REST request to update library: {}", library);

        if (library.getId() == null) {
            return createLibraryUsingPOST(networkShortcut, library);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibLibrary entity = libLibraryMapper.DTO2DB(library, corNetwork);
        LibLibrary resultDB = libLibraryService.createOrUpdateLibrary(entity);
        LibLibraryDTO libraryDAO = libLibraryMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
            .body(libraryDAO);
    }

    @Override
    public ResponseEntity<List<LibLibraryDTO>> getAllLibrariesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all LibLibraryDTO");
        List<LibLibrary> libraries = libLibraryService.findLibraries(networkShortcut, pagable);
        return ResponseEntity.ok()
            .body(libLibraryMapper.DBs2DTOs(libraries));
    }

    @Override
    public ResponseEntity<LibLibraryDTO> createLibraryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "library", required = true) @Valid @RequestBody LibLibraryDTO library) throws URISyntaxException {
        log.debug("REST request to create library: {}", library);
        if (library.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("libLibrary", "idexists", "A new libLibrary cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibLibrary entity = libLibraryMapper.DTO2DB(library, corNetwork);
        LibLibrary resultDB = libLibraryService.createOrUpdateLibrary(entity);
        LibLibraryDTO libraryDAO = libLibraryMapper.DB2DTO(resultDB);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/library/" + libraryDAO.getShortcut()))
            .body(libraryDAO);
    }

    @Override
    public ResponseEntity<Void> deleteLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        log.debug("REST request to delete LIBLibrary : {}", libraryPrefix);
        libLibraryService.deleteLibrary(libraryPrefix, networkShortcut);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<LibLibraryDTO> getLibraryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        log.debug("REST request to get library: {}", libraryPrefix);
        LibLibrary library = libLibraryService.findLibrary(networkShortcut, libraryPrefix);
        LibLibraryDTO dto = libLibraryMapper.DB2DTO(library);
        return Optional.ofNullable(dto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
