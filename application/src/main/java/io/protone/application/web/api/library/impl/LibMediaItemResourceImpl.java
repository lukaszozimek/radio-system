package io.protone.application.web.api.library.impl;


import io.protone.application.web.api.library.LibMediaItemResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.library.api.dto.LibMediaItemDTO;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.mapper.LibItemMapper;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.library.service.LibItemService;
import io.swagger.annotations.ApiParam;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.validation.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by grzesiek on 27.01.2017.
 */

//TODO: How to deal with channel in this case
@RestController
public class LibMediaItemResourceImpl implements LibMediaItemResource {

    private final Logger log = LoggerFactory.getLogger(LibMediaItemResourceImpl.class);

    @Inject
    private LibItemService libItemService;

    @Inject
    private LibItemMapper libMediaItemMapper;

    @Inject
    private LibMediaItemThinMapper libMediaItemThinMapper;
    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<LibMediaItemDTO> updateItemWithImagesByNetworShortcutAndLibraryPrefixUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                         @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                         @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx,
                                                                                                         @ApiParam(value = "mediaItem", required = true) @RequestPart("mediaItem") @Valid LibMediaItemDTO mediaItem,
                                                                                                         @ApiParam(value = "covers") @RequestPart("covers") MultipartFile[] covers) throws IOException {

        if (mediaItem.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("LibMediaItem", "missingID", "Can't edit Element if File doesn't exist")).body(null);

        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibMediaItem requestEntity = libMediaItemMapper.DTO2DB(mediaItem, corNetwork);
        LibMediaItem entity = libItemService.update(covers, requestEntity, corNetwork);
        LibMediaItemDTO response = libMediaItemMapper.DB2DTO(entity);
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    public ResponseEntity<LibMediaItemDTO> updateItemByWithoutImagesNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                           @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                           @ApiParam(value = "mediaItem", required = true) @RequestBody @Valid LibMediaItemDTO mediaItem) throws IOException {
        if (mediaItem.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("LibMediaItem", "missingID", "Can't edit Element if File doesn't exist")).body(null);

        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibMediaItem requestEntity = libMediaItemMapper.DTO2DB(mediaItem, corNetwork);
        LibMediaItem entity = libItemService.update(requestEntity, corNetwork);
        LibMediaItemDTO response = libMediaItemMapper.DB2DTO(entity);
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    public ResponseEntity<Void> moveMediaItemUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx,
                                                                                                @ApiParam(value = "libraryShortcut", required = true) @PathVariable("libraryShortcut") String libraryShortcut) {
        log.debug("REST request to move Libarary Item {} from {} to {}", idx, libraryPrefix, libraryShortcut);
        libItemService.moveMediaItem(networkShortcut, libraryPrefix, idx, libraryShortcut);
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<List<LibMediaItemThinDTO>> getAllItemsByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                         @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                         @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all LibLibraryDTO");
        List<LibMediaItem> entities = libItemService.getMediaItems(networkShortcut, libraryPrefix, pagable);
        List<LibMediaItemThinDTO> response = libMediaItemThinMapper.DBs2DTOs(entities);
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    public ResponseEntity<List<LibMediaItemThinDTO>> uploadItemsByNetworShortcutAndLibraryPrefix(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "files", required = true) @PathParam("files") MultipartFile[] files) throws IOException, TikaException, SAXException {
        List<LibMediaItem> entities = libItemService.upload(networkShortcut, libraryPrefix, files);
        List<LibMediaItemThinDTO> response = libMediaItemThinMapper.DBs2DTOs(entities);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<byte[]> streamItemByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) throws IOException {
        byte[] data = libItemService.download(networkShortcut, libraryPrefix, idx);
        return Optional.ofNullable(data)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<LibMediaItemDTO> updateItemByNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                              @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                              @ApiParam(value = "mediaItem", required = true) @RequestBody LibMediaItemDTO mediaItem) {
        return null;
    }

    @Override
    public ResponseEntity<List<LibMediaItemThinDTO>> getAllItemsByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                     @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                     @ApiParam(value = "pagable", required = true) Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<LibMediaItemDTO> getItemsByNetworShortcutAndChannelShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                       @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteItemByNetworShortcutAndChannelShortcutAndLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                  @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                  @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) {
        return null;
    }

    @Override
    public ResponseEntity<LibMediaItemDTO> getItemByNetworShortcutAndChannelShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                      @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                      @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) {
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getItemStreamByNetworShortcutAndChannelShortcutAndLibraryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                    @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                    @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) {
        return null;
    }

    @Override
    public ResponseEntity<LibMediaItemDTO> getItemByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) {
        log.debug("REST request to get item: {}", idx);
        LibMediaItem entity = libItemService.getMediaItem(networkShortcut, libraryPrefix, idx);
        LibMediaItemDTO response = libMediaItemMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<Void> deleteItemByNetworShortcutAndLibrarUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) {
        log.debug("REST request to delete item : {}", idx);
        libItemService.deleteItem(networkShortcut, libraryPrefix, idx);
        return ResponseEntity.ok().build();
    }

    private LibMediaItemDTO validate(LibMediaItemDTO libMediaItemDTO) throws IOException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<LibMediaItemDTO>> constraintViolations = validator.validate(libMediaItemDTO);
        if (constraintViolations.isEmpty()) {
            return libMediaItemDTO;
        } else {
            throw new ValidationException();
        }

    }

}
