package io.protone.custom.web.rest.network.channel.impl;

import io.protone.custom.service.LibLibraryService;
import io.protone.custom.service.dto.LibraryPT;
import io.protone.custom.service.mapper.CustomLibLibraryMapperExt;
import io.protone.custom.web.rest.network.channel.ApiChannelLibrary;
import io.protone.domain.LibLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiChannelLibraryImpl implements ApiChannelLibrary {
    private final Logger log = LoggerFactory.getLogger(ApiChannelLibraryImpl.class);

    @Inject
    private CustomLibLibraryMapperExt libraryMapper;

    @Inject
    private LibLibraryService libraryService;

    @Override
    public ResponseEntity<List<LibraryPT>> getAllLibrariesForChannelUsingGET(String networkShortcut, String channelShortcut, Pageable pagable) {
        log.debug("REST request to get all LibraryPT");
        List<LibLibrary> libraries = libraryService.findLibrary(networkShortcut);
        return ResponseEntity.ok()
            .body(libraryMapper.DBs2DTOs(libraries));
    }

    @Override
    public ResponseEntity<LibraryPT> updateLibraryForChannelUsingPUT(String networkShortcut, String channelShortcut, LibraryPT library) {
        log.debug("REST request to update library: {}", library);

        if (library.getId() == null)
            return createLibraryForChannelUsingPOST(networkShortcut, channelShortcut, library);

        libraryMapper.DTO2DB(library);
        LibLibrary resultDB = libraryService.createOrUpdateLibrary(networkShortcut, library);
        LibraryPT libraryDAO = libraryMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
            .body(libraryDAO);
    }

    @Override
    public ResponseEntity<LibraryPT> createLibraryForChannelUsingPOST(String networkShortcut, String channelShortcut, LibraryPT library) {
        log.debug("REST request to create library: {}", library);
        LibLibrary resultDB = libraryService.createOrUpdateLibrary(networkShortcut, library);
        LibraryPT libraryDAO = libraryMapper.DB2DTO(resultDB);
        return ResponseEntity.ok()
            .body(libraryDAO);
    }

    @Override
    public ResponseEntity<Void> deleteLibraryForChannelUsingDELETE(String networkShortcut, String channelShortcut, String libraryPrefix) {
        log.debug("REST request to delete LIBLibrary : {}", libraryPrefix);
        libraryService.deleteLibrary(networkShortcut, libraryPrefix);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<LibraryPT> getLibraryForChannelUsingGET(String networkShortcut, String channelShortcut, String libraryPrefix) {
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