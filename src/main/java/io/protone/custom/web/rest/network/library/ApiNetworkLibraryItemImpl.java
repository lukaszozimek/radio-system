package io.protone.custom.web.rest.network.library;

import io.protone.custom.service.ItemService;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.LibResponseEntity;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by grzesiek on 27.01.2017.
 */
public class ApiNetworkLibraryItemImpl implements ApiNetworkLibraryItem {

    private final Logger log = LoggerFactory.getLogger(ApiNetworkLibraryItemImpl.class);

    @Inject
    ItemService itemService;

    @Override
    public ResponseEntity<LibItemPT> updateItemByNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "mediaItem", required = true) @RequestBody LibItemPT mediaItem) {
        return null;
    }

    @Override
    public ResponseEntity<List<LibItemPT>> getAllItemsByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        log.debug("REST request to get all LibraryPT");
        List<LibItemPT> items = itemService.getItem(networkShortcut, libraryPrefix);
        return ResponseEntity.ok()
            .body(items);
    }

    @Override
    public ResponseEntity<LibItemPT> getItemByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) {
        log.debug("REST request to get item: {}", idx);
        LibItemPT item = itemService.getItem(networkShortcut, libraryPrefix, idx);
        return Optional.ofNullable(item)
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

    @Override
    public ResponseEntity<LibResponseEntity> getItemStreamByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix, @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) {
        return null;
    }
}
