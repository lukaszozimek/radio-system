package io.protone.application.web.api.library.impl;

import io.protone.application.web.api.library.LibFileItemResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.library.api.dto.LibFileItemDTO;
import io.protone.library.api.dto.thin.LibFileItemThinDTO;
import io.protone.library.domain.LibFileItem;
import io.protone.library.mapper.LibFileItemMapper;
import io.protone.library.service.LibFileItemService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class LibFileItemResourceImpl implements LibFileItemResource {

    private final Logger log = LoggerFactory.getLogger(LibFileItemResourceImpl.class);

    @Inject
    private LibFileItemService libFileItemService;

    @Inject
    private LibFileItemMapper libFileItemMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<LibFileItemDTO> updateFileNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                           @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                           @ApiParam(value = "fileItem", required = true) @RequestBody @Valid LibFileItemDTO fileItem
    ) throws IOException {
        if (fileItem.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("LibFileItem", "missingID", "Can't edit Element if File doesn't exist")).body(null);

        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibFileItem requestEntity = libFileItemMapper.DTO2DB(fileItem, corNetwork);
        LibFileItem entity = libFileItemService.update(requestEntity);
        LibFileItemDTO response = libFileItemMapper.DB2DTO(entity);
        return ResponseEntity.ok()
                .body(response);
    }


    @Override
    public ResponseEntity<Void> moveFileItemUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                     @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx,
                                                     @ApiParam(value = "libraryShortcut", required = true) @PathVariable("libraryShortcut") String libraryShortcut) {
        log.debug("REST request to move Libarary Item {} from {} to {}", idx, libraryPrefix, libraryShortcut);
        libFileItemService.moveFileItem(networkShortcut, libraryPrefix, idx, libraryShortcut);
        return ResponseEntity.ok().build();

    }


    @Override
    public ResponseEntity<List<LibFileItemThinDTO>> getAllFilesByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                        @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all LibMediaLibraryDTO");
        Slice<LibFileItem> entities = libFileItemService.findAllLibFileItems(networkShortcut, libraryPrefix, pagable);
        List<LibFileItemThinDTO> response = libFileItemMapper.DBs2ThinDTOs(entities.getContent());
        return ResponseEntity.ok().headers(PaginationUtil.generateSliceHttpHeaders(entities))
                .body(response);
    }


    @Override
    public ResponseEntity<LibFileItemThinDTO> uploadItemsByNetworShortcutAndLibraryPrefix(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                          @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                          @ApiParam(value = "files", required = true) @PathParam("files") MultipartFile files) throws Exception {
        LibFileItem entities = libFileItemService.uploadFileItem(networkShortcut, libraryPrefix, files);
        LibFileItemThinDTO response = libFileItemMapper.DB2ThinDTO(entities);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<LibFileItemDTO> getFileByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                   @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) {
        log.debug("REST request to get item: {}", idx);
        LibFileItem entity = libFileItemService.findLibFileItem(networkShortcut, libraryPrefix, idx);
        LibFileItemDTO response = libFileItemMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<Void> deleteFileByNetworShortcutAndLibrarUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                               @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) {
        log.debug("REST request to delete item : {}", idx);
        libFileItemService.deleteFile(networkShortcut, libraryPrefix, idx);
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<byte[]> downloadFileByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) throws IOException {
        byte[] data = libFileItemService.download(networkShortcut, libraryPrefix, idx);
        return Optional.ofNullable(data)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<LibFileItemDTO> updateFileByNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                             @ApiParam(value = "fileItem", required = true) @RequestBody LibFileItemDTO fileItem) {
        return null;
    }


    @Override
    public ResponseEntity<List<LibFileItemThinDTO>> getAllFilesByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                        @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable) {
        return null;
    }


    @Override
    public ResponseEntity<LibFileItemDTO> getFileByNetworShortcutAndChannelShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                     @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        return null;
    }


    @Override
    public ResponseEntity<Void> deleteFileByNetworShortcutAndChannelShortcutAndLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                  @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                  @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) {
        return null;
    }


    @Override
    public ResponseEntity<LibFileItemDTO> getFileByNetworShortcutAndChannelShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                     @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                     @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) {
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getFileStreamByNetworShortcutAndChannelShortcutAndLibraryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                    @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                    @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) {
        return null;
    }


}
