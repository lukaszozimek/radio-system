package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.CorDictionaryPT;
import io.protone.service.mapper.CorDictionaryMapper;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryCoreDictionary;
import io.protone.domain.CorDictionary;
import io.protone.domain.CorDictionaryType;
import io.protone.domain.CorNetwork;
import io.protone.repository.CorDictionaryRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


@RestController
public class ApiDictionaryCoreDictionaryImpl implements ApiDictionaryCoreDictionary {
    private final Logger log = LoggerFactory.getLogger(ApiDictionaryCountryImpl.class);

    @Inject
    private CorNetworkService corNetworkService;
    @Inject
    private CorDictionaryMapper customCorDictionaryMapper;
    @Inject
    private CorDictionaryRepository corDictionaryRepository;

    @Override
    public ResponseEntity<CorDictionaryPT> updateDictionaryValueUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                         @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                         @ApiParam(value = "corDictionaryPT", required = true) @RequestBody CorDictionaryPT corDictionaryPT) {
        log.debug("REST request to update CorDictionary : {}", corDictionaryPT);
        if (corDictionaryPT.getId() == null) {
            return createDictionaryValueUsingPOST(networkShortcut, module, type, corDictionaryPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorDictionary corDictionary = customCorDictionaryMapper.DTO2DB(corDictionaryPT);
        corDictionary.setNetwork(corNetwork);
        corDictionary = corDictionaryRepository.save(corDictionary);
        CorDictionaryPT result = customCorDictionaryMapper.DB2DTO(corDictionary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("CorDictionary", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CorDictionaryPT> createDictionaryValueUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                          @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                          @ApiParam(value = "corDictionaryPT", required = true) @RequestBody CorDictionaryPT corDictionaryPT) {
        log.debug("REST request to save CorDictionary : {}", corDictionaryPT);
        if (corDictionaryPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CorDictionary", "idexists", "A new CorDictionary cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorDictionary corDictionary = customCorDictionaryMapper.DTO2DB(corDictionaryPT);
        corDictionary.setNetwork(corNetwork);
        corDictionary = corDictionaryRepository.save(corDictionary);
        CorDictionaryPT result = customCorDictionaryMapper.DB2DTO(corDictionary);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deleteDictionaryValueUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                 @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CorDictionary : {}", id);
        corDictionaryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CorDictionary", id.toString())).build();
    }

    @Override
    public ResponseEntity<List<CorDictionaryPT>> getAllDictionaryValueUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                               @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                               @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get CorDictionary : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<CorDictionary> corDictionaries = corDictionaryRepository.findAllByCorDictionaryTypeAndNetwork(type, corNetwork);
        List<CorDictionaryPT> dictionaryPTS = customCorDictionaryMapper.DBs2DTOs(corDictionaries);
        return Optional.ofNullable(dictionaryPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CorDictionaryPT> getDictionaryValueGET(String networkShortcut, String module, String type, Long id) {
        log.debug("REST request to get CorDictionary : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorDictionary corDictionary = corDictionaryRepository.findByIdAndCorDictionaryTypeAndNetwork(id, new CorDictionaryType().name(type), corNetwork);
        CorDictionaryPT corDictionaryPT = customCorDictionaryMapper.DB2DTO(corDictionary);
        return Optional.ofNullable(corDictionaryPT)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
