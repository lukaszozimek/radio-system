package io.protone.custom.web.rest.network.library.impl;

import io.protone.service.library.LibLibraryService;
import io.protone.custom.service.dto.LibraryPT;
import io.protone.domain.CorNetwork;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.mapper.LibLibraryMapper;
import io.protone.custom.web.rest.network.library.ApiNetworkLibrary;
import io.protone.domain.LibLibrary;
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
import java.util.List;
import java.util.Optional;

@RestController
public class ApiNetworkLibraryImpl implements ApiNetworkLibrary {

    private final Logger log = LoggerFactory.getLogger(ApiNetworkLibraryImpl.class);

    @Inject
    private LibLibraryMapper libraryMapper;

    @Inject
    private LibLibraryService libraryService;
    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<LibraryPT> updateLibraryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "library", required = true) @RequestBody LibraryPT library) {
        log.debug("REST request to update library: {}", library);

        if (library.getId() == null)
            return createLibraryUsingPOST(networkShortcut, library);

        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        LibLibrary entity = libraryMapper.DTO2DB(library, corNetwork);
        LibLibrary resultDB = libraryService.createOrUpdateLibrary(entity);
        LibraryPT libraryDAO = libraryMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
            .body(libraryDAO);
    }

    @Override
    public ResponseEntity<List<LibraryPT>> getAllLibrariesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all LibraryPT");
        List<LibLibrary> libraries = libraryService.findLibraries(networkShortcut, pagable);
        return ResponseEntity.ok()
            .body(libraryMapper.DBs2DTOs(libraries));
    }

    @Override
    public ResponseEntity<LibraryPT> createLibraryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "library", required = true) @RequestBody LibraryPT library) {
        log.debug("REST request to create library: {}", library);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        LibLibrary entity = libraryMapper.DTO2DB(library, corNetwork);
        LibLibrary resultDB = libraryService.createOrUpdateLibrary(entity);
        LibraryPT libraryDAO = libraryMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
            .body(libraryDAO);
    }

    @Override
    public ResponseEntity<Void> deleteLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        log.debug("REST request to delete LIBLibrary : {}", libraryPrefix);
        libraryService.deleteLibrary(networkShortcut, libraryPrefix);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<LibraryPT> getLibraryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        log.debug("REST request to get library: {}", libraryPrefix);
        LibLibrary library = libraryService.findLibrary(networkShortcut, libraryPrefix);
        LibraryPT dto = libraryMapper.DB2DTO(library);
        return Optional.ofNullable(dto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
