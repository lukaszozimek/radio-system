package io.protone.custom.web.rest.network.library;

import io.protone.custom.service.LibraryService;
import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.LibraryPT;
import io.protone.custom.service.mapper.CustomLibLibraryMapperExt;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class ApiNetworkLibraryImpl implements ApiNetworkLibrary {

    @Inject
    private CustomLibLibraryMapperExt customLibLibraryMapper;
    @Inject
    private LibraryService libraryService;

    @Override
    public ResponseEntity<LibraryPT> updateLibraryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "library", required = true) @RequestBody LibraryPT library) {
        return ResponseEntity.ok()
            .body(customLibLibraryMapper.DB2DTO(libraryService.createOrUpdateLibrary(networkShortcut, library)));
    }

    @Override
    public ResponseEntity<List<LibraryPT>> getAllLibrariesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return ResponseEntity.ok()
            .body(libraryService.findLibrary(networkShortcut).stream().map(libLibrary -> customLibLibraryMapper.DB2DTO(libLibrary)).collect(toList()));
    }

    @Override
    public ResponseEntity<LibraryPT> createLibraryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "library", required = true) @RequestBody LibraryPT library) {
        return ResponseEntity.ok()
            .body(customLibLibraryMapper.DB2DTO(libraryService.createOrUpdateLibrary(networkShortcut, library)));
    }

    @Override
    public ResponseEntity<Void> deleteLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        libraryService.deleteLibrary(networkShortcut, libraryPrefix);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<LibraryPT> getLibraryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        return ResponseEntity.ok()
            .body(customLibLibraryMapper.DB2DTO(libraryService.findLibrary(networkShortcut, libraryPrefix)));
    }
}
