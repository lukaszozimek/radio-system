package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.ConfTagPT;
import io.protone.custom.service.mapper.CustomCORTagMapper;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryTag;
import io.protone.domain.CORTag;
import io.protone.repository.CCORNetworkRepository;
import io.protone.repository.CORTagRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiDictionaryTagImpl implements ApiDictionaryTag {
    private final Logger log = LoggerFactory.getLogger(ApiDictionaryTagImpl.class);

    @Inject
    private CORTagRepository cORTagRepository;
    @Inject
    private NetworkService networkService;
    @Inject
    private CustomCORTagMapper cORTagMapper;

    @Override
    public ResponseEntity<ConfTagPT> updateTagUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "tagDTO", required = true) @RequestBody ConfTagPT tagDTO) {
        log.debug("REST request to update CORTag : {}", tagDTO);
        if (tagDTO.getId() == null) {
            return createTagUsingPOST(networkShortcut, tagDTO);
        }
        CORTag cORTag = cORTagMapper.cORTagDTOToCORTag(tagDTO);
        cORTag = cORTagRepository.save(cORTag);
        ConfTagPT result = cORTagMapper.cORTagToCORTagDTO(cORTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORTag", cORTag.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfTagPT> createTagUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "tagDTO", required = true) @RequestBody ConfTagPT tagDTO) {
        log.debug("REST request to save CORTag : {}", tagDTO);
        if (tagDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORTag", "idexists", "A new cORTag cannot already have an ID")).body(null);
        }
        CORTag cORTag = cORTagMapper.cORTagDTOToCORTag(tagDTO);
        cORTag = cORTagRepository.save(cORTag);
        ConfTagPT result = cORTagMapper.cORTagToCORTagDTO(cORTag);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<List<ConfTagPT>> getAllTagsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CORTags");
        List<CORTag> cORTags = cORTagRepository.findAll();
        return ResponseEntity.ok().body(cORTagMapper.cORTagsToCORTagDTOs(cORTags));
    }
}
