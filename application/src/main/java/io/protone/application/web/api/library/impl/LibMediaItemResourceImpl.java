package io.protone.application.web.api.library.impl;


import io.protone.application.web.api.library.LibMediaItemResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.service.CorChannelService;
import io.protone.library.api.dto.LibMediaItemDTO;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.mapper.LibMediaItemMapper;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.library.service.LibMediaItemService;
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
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by grzesiek on 27.01.2017.
 */

//TODO: How to deal with channel in this case
@RestController
public class LibMediaItemResourceImpl implements LibMediaItemResource {

    private final Logger log = LoggerFactory.getLogger(LibMediaItemResourceImpl.class);

    @Inject
    private LibMediaItemService libMediaItemService;

    @Inject
    private LibMediaItemMapper libMediaItemMapper;

    @Inject
    private LibMediaItemThinMapper libMediaItemThinMapper;
    @Inject
    private CorChannelService corChannelService;

    @Override
    public ResponseEntity<LibMediaItemDTO> updateItemWithImagesByNetworShortcutAndLibraryPrefixUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                         @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                         @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx,
                                                                                                         @ApiParam(value = "fileItem", required = true) @RequestPart("fileItem") @Valid LibMediaItemDTO mediaItem,
                                                                                                         @ApiParam(value = "covers") @RequestPart("covers") MultipartFile[] covers) throws IOException {
        if (mediaItem.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("LibMediaItem", "missingID", "Can't edit Element if File doesn't exist")).body(null);

        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        LibMediaItem requestEntity = libMediaItemMapper.DTO2DB(mediaItem, corChannel);
        LibMediaItem entity = libMediaItemService.update(covers, requestEntity, corChannel);
        LibMediaItemDTO response = libMediaItemMapper.DB2DTO(entity);
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    public ResponseEntity<LibMediaItemThinDTO> updateItemContentByNetworShortcutAndLibraryPrefixUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                          @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                          @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx,
                                                                                                          @ApiParam(value = "files", required = true) @PathParam("files") MultipartFile files) throws IOException, TikaException, SAXException {
        LibMediaItem entitiy = libMediaItemService.updateItemContent(organizationShortcut, channelShortcut, libraryPrefix, idx, files);
        LibMediaItemThinDTO response = libMediaItemThinMapper.DB2DTO(entitiy);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<LibMediaItemDTO> updateItemByWithoutImagesNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                           @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                           @ApiParam(value = "fileItem", required = true) @RequestBody @Valid LibMediaItemDTO mediaItem) throws IOException {
        if (mediaItem.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("LibMediaItem", "missingID", "Can't edit Element if File doesn't exist")).body(null);

        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        LibMediaItem requestEntity = libMediaItemMapper.DTO2DB(mediaItem, corChannel);
        LibMediaItem entity = libMediaItemService.update(requestEntity, corChannel);
        LibMediaItemDTO response = libMediaItemMapper.DB2DTO(entity);
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    public ResponseEntity<Void> moveMediaItemUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                      @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                      @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx,
                                                      @ApiParam(value = "libraryShortcut", required = true) @PathVariable("libraryShortcut") String libraryShortcut) {
        log.debug("REST request to move Libarary Item {} from {} to {}", idx, libraryPrefix, libraryShortcut);
        libMediaItemService.moveMediaItem(organizationShortcut, channelShortcut, libraryPrefix, idx, libraryShortcut);
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<List<LibMediaItemThinDTO>> getAllItemsByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                         @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                         @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all LibMediaLibraryDTO");
        Slice<LibMediaItem> entities = libMediaItemService.getMediaItems(organizationShortcut, channelShortcut, libraryPrefix, pagable);
        List<LibMediaItemThinDTO> response = libMediaItemThinMapper.DBs2DTOs(entities.getContent());
        return ResponseEntity.ok().headers(PaginationUtil.generateSliceHttpHeaders(entities))
                .body(response);
    }

    @Override
    public ResponseEntity<LibMediaItemThinDTO> uploadItemsByNetworShortcutAndLibraryPrefix(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                           @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                           @ApiParam(value = "files", required = true) @PathParam("files") MultipartFile files) throws IOException, TikaException, SAXException {
        LibMediaItem entities = libMediaItemService.upload(organizationShortcut, channelShortcut, libraryPrefix, files);
        LibMediaItemThinDTO response = libMediaItemThinMapper.DB2DTO(entities);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<byte[]> streamItemByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "libraryPrefix", required = true)
                                                                              @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) throws IOException {
        byte[] data = libMediaItemService.download(organizationShortcut, channelShortcut, libraryPrefix, idx);
        return Optional.ofNullable(data)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<LibMediaItemDTO> getItemByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) {
        log.debug("REST request to get item: {}", idx);
        LibMediaItem entity = libMediaItemService.getMediaItem(organizationShortcut, channelShortcut, libraryPrefix, idx);
        LibMediaItemDTO response = libMediaItemMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<Void> deleteItemByNetworShortcutAndLibrarUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) {
        log.debug("REST request to delete item : {}", idx);
        libMediaItemService.deleteItem(organizationShortcut, channelShortcut, libraryPrefix, idx);
        return ResponseEntity.ok().build();
    }
}
