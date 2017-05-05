package io.protone.custom.web.rest.network.channel.impl;

import io.protone.web.rest.dto.library.LibMediaItemDTO;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.LibItemService;
import io.protone.custom.web.rest.network.channel.ApiChannelLibraryItem;
import io.protone.custom.web.rest.network.library.impl.LibMediaItemResourceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class ApiChannelLibraryItemImpl implements ApiChannelLibraryItem {

    private final Logger log = LoggerFactory.getLogger(LibMediaItemResourceImpl.class);

    @Inject
    private LibItemService itemService;

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<LibMediaItemDTO> updateItemByNetworShortcutAndLibraryPrefixUsingPUT(String networkShortcut, String libraryPrefix, String channelShortcut, LibMediaItemDTO mediaItem) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteItemsByNetworShortcutAndChannelShortcutAndLibraryUsingDELETE(String networkShortcut, String channelShortcut, String libraryPrefix, Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<LibMediaItemDTO> getItemsByNetworShortcutAndChannelShortcutAndLibrarUsingGET(String networkShortcut, String channelShortcut, String libraryPrefix) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteItemByNetworShortcutAndChannelShortcutAndLibraryUsingDELETE(String networkShortcut, String channelShortcut, String libraryPrefix, String idx) {
        return null;
    }

    @Override
    public ResponseEntity<LibMediaItemDTO> getItemByNetworShortcutAndChannelShortcutAndLibrarUsingGET(String networkShortcut, String channelShortcut, String libraryPrefix, String idx) {
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getItemStreamByNetworShortcutAndChannelShortcutAndLibraryUsingGET(String networkShortcut, String channelShortcut, String libraryPrefix, String idx) {
        return null;
    }
}
