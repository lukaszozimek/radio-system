package io.protone.custom.web.rest.network.library;

import io.protone.custom.service.LibItemService;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.SchBlockPT;
import io.protone.custom.service.dto.SchEmissionPT;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Random;
import java.util.List;
import java.util.Set;

/**
 * Created by gk on 05/04/2017.
 */
@RestController
public class ApiNetworkLibraryBlockImpl implements ApiNetworkLibraryBlock {

    private final Logger log = LoggerFactory.getLogger(ApiNetworkLibraryItemImpl.class);
    private static final Random rnd = new Random();

    @Inject
    private LibItemService itemService;

    @Override
    public ResponseEntity<SchBlockPT> getRandomBlockFromSelectedLibrary(
        @ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
        @ApiParam(value = "size", required = false) @RequestParam(value = "size", required = false) Integer size,
        @ApiParam(value = "maxLength", required = false) @RequestParam(value = "maxLength", required = false) Integer maxLength) {

        log.debug("REST request to get all LibraryPT");
        List<LibItemPT> items = itemService.getItem(networkShortcut, libraryPrefix);
        Set<LibItemPT> set = new HashSet<>();

        SchBlockPT result = new SchBlockPT();

        if (items.size() == 0)
            return ResponseEntity.ok().body(result);

        int defaultSize = 5;
        int cnt = 0;

        if (size != null) {
            defaultSize = size;
            int currentSize = 0;
            do {
                int rndId = rnd.nextInt(items.size());
                LibItemPT rndItem = items.get(rndId);
                if (!set.contains(rndItem)) {
                    result.addEmission(buildEmission(rndItem));
                    currentSize += 1;
                }
                cnt++;
            }
            while (cnt < 100 && currentSize < defaultSize);
        }
        else {
            int currentLength = 0;
            do {
                int rndId = rnd.nextInt(items.size());
                LibItemPT rndItem = items.get(rndId);
                if (!set.contains(rndItem)) {
                    result.addEmission(buildEmission(rndItem));
                    currentLength += rndItem.getLength();
                }
                cnt++;
            }
            while (cnt < 100 && currentLength < maxLength);
        }

        return ResponseEntity.ok().body(result);
    }

    private SchEmissionPT buildEmission(LibItemPT rndItem) {
        return new SchEmissionPT()
            .name("EmissionOf_" + rndItem.getName())
            .mediaItem(rndItem)
            .length(rndItem.getLength().longValue());
    }
}
