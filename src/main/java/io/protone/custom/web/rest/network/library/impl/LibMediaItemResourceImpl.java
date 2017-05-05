package io.protone.custom.web.rest.network.library.impl;

import io.protone.custom.service.dto.LibMediaItemDTO;
import io.protone.domain.LibMediaItem;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.LibItemService;
import io.protone.custom.web.rest.network.library.LibMediaItemResource;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.mapper.LibItemMapper;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by grzesiek on 27.01.2017.
 */
@RestController
public class LibMediaItemResourceImpl implements LibMediaItemResource {

    private final Logger log = LoggerFactory.getLogger(LibMediaItemResourceImpl.class);

    @Inject
    private LibItemService itemService;

    @Inject
    private LibItemMapper libItemMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<LibMediaItemDTO> updateItemByNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "mediaItem", required = true) @RequestBody LibMediaItemDTO mediaItem) {
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibMediaItem requestEntity = libItemMapper.DTO2DB(mediaItem, corNetwork);
        LibMediaItem entity = itemService.update(requestEntity, corNetwork);
        LibMediaItemDTO response = libItemMapper.DB2DTO(entity);
        return ResponseEntity.ok()
            .body(response);
    }

    @Override
    public ResponseEntity<List<LibMediaItemDTO>> getAllItemsByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                     @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                     @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all LibLibraryDTO");
        List<LibMediaItem> entities = itemService.getMediaItems(networkShortcut, libraryPrefix, pagable);
        List<LibMediaItemDTO> response = libItemMapper.DBs2DTOs(entities);

        return ResponseEntity.ok()
            .body(response);
    }

    @Override
    public ResponseEntity<List<LibMediaItemDTO>> uploadItemsByNetworShortcutAndLibraryPrefix(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "files", required = true) @PathParam("files") MultipartFile[] files) throws IOException {
        List<LibMediaItem> entities = itemService.upload(networkShortcut, libraryPrefix, files);
        List<LibMediaItemDTO> response = libItemMapper.DBs2DTOs(entities);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<byte[]> streamItemByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) throws IOException {
        byte[] data = itemService.download(networkShortcut, libraryPrefix, idx);
        return Optional.ofNullable(data)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<LibMediaItemDTO> getItemByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) {
        log.debug("REST request to get item: {}", idx);
        LibMediaItem entity = itemService.getMediaItem(networkShortcut, libraryPrefix, idx);
        LibMediaItemDTO response = libItemMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteItemByNetworShortcutAndLibrarUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) {
        log.debug("REST request to delete item : {}", idx);
        itemService.deleteItem(networkShortcut, libraryPrefix, idx);
        return ResponseEntity.ok().build();
    }
}
