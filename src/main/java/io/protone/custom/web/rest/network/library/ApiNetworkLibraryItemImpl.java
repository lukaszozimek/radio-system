package io.protone.custom.web.rest.network.library;

import io.protone.custom.service.ItemService;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.LibResponseEntity;
import io.protone.custom.service.mapper.CustomLIBMediaItemMapper;
import io.protone.custom.service.mapper.ext.CustomLIBLibraryMapperExt;
import io.protone.domain.LIBMediaItem;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by grzesiek on 27.01.2017.
 */
public class ApiNetworkLibraryItemImpl implements ApiNetworkLibraryItem {

    private final Logger log = LoggerFactory.getLogger(ApiNetworkLibraryItemImpl.class);

    @Inject
    ItemService itemService;

    @Inject
    CustomLIBMediaItemMapper itemMapper;

    @Override
    public ResponseEntity<LibItemPT> updateItemByNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "mediaItem", required = true) @RequestBody LibItemPT mediaItem) {
        return null;
    }

    @Override
    public ResponseEntity<List<LibItemPT>> getAllItemsByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        log.debug("REST request to get all MediaItems");
        List<LIBMediaItem> mediaItems = itemService.findItems(networkShortcut, libraryPrefix);
        return ResponseEntity.ok()
            .body(itemMapper.);
    }

    @Override
    public ResponseEntity<LibItemPT> getItemByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteItemByNetworShortcutAndLibrarUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) {
        return null;
    }

    @Override
    public ResponseEntity<LibResponseEntity> getItemStreamByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) {
        return null;
    }
}
