package io.protone.custom.web.rest.network.library;

import io.protone.custom.service.dto.LibraryPT;
import io.protone.custom.service.mapper.ext.CustomLIBLibraryMapperExt;
import io.protone.domain.LIBLibrary;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    CustomLIBLibraryMapperExt customLIBLibraryMapper;

    @Inject
    LibraryService libraryService;

    @Override
    public ResponseEntity<LibraryPT> updateLibraryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "library", required = true) @RequestBody LibraryPT library) {
        log.debug("REST request to update library: {}", library);

        if (library.getId() == null)
            return createLibraryUsingPOST(networkShortcut, library);

        customLIBLibraryMapper.libLibraryPTToLIBLibrary(library);
        LIBLibrary resultDB = libraryService.createOrUpdateLibrary(networkShortcut, library);
        LibraryPT libraryDAO = customLIBLibraryMapper.libLibrary2LibraryPT(resultDB);
        return ResponseEntity.ok()
            .body(libraryDAO);
    }

    @Override
    public ResponseEntity<List<LibraryPT>> getAllLibrariesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all LibraryPT");
        List<LIBLibrary> libraries = libraryService.findLibrary(networkShortcut);
        return ResponseEntity.ok()
            .body(customLIBLibraryMapper.libLibraries2LibraryPTs(libraries));
    }

    @Override
    public ResponseEntity<LibraryPT> createLibraryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "library", required = true) @RequestBody LibraryPT library) {
        log.debug("REST request to create library: {}", library);
        LIBLibrary resultDB = libraryService.createOrUpdateLibrary(networkShortcut, library);
        LibraryPT libraryDAO = customLIBLibraryMapper.libLibrary2LibraryPT(resultDB);
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
        LIBLibrary library = libraryService.findLibrary(networkShortcut, libraryPrefix);
        LibraryPT dto = customLIBLibraryMapper.libLibrary2LibraryPT(library);
        return Optional.ofNullable(dto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
