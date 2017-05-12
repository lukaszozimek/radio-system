package io.protone.web.api.cor.impl;

import io.protone.domain.CorModule;
import io.protone.web.api.cor.CorDictionaryResource;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.rest.mapper.CorDictionaryMapper;
import io.protone.domain.CorDictionary;
import io.protone.domain.CorDictionaryType;
import io.protone.domain.CorNetwork;
import io.protone.repository.cor.CorDictionaryRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
public class CorDictionaryResourceImpl implements CorDictionaryResource {
    private final Logger log = LoggerFactory.getLogger(CorDictionaryCountryResourceImpl.class);

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorDictionaryMapper corDictionaryMapper;

    @Inject
    private CorDictionaryRepository corDictionaryRepository;

    @Override
    public ResponseEntity<CorDictionaryDTO> updateDictionaryValueUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                          @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                          @ApiParam(value = "corDictionaryDTO", required = true) @Valid @RequestBody CorDictionaryDTO corDictionaryDTO) throws URISyntaxException {
        log.debug("REST request to update CorDictionary : {}", corDictionaryDTO);
        if (corDictionaryDTO.getId() == null) {
            return createDictionaryValueUsingPOST(networkShortcut, module, type, corDictionaryDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorDictionary corDictionary = corDictionaryMapper.DTO2DB(corDictionaryDTO, corNetwork, new CorModule().name(module), new CorDictionaryType().name(type));
        corDictionary = corDictionaryRepository.save(corDictionary);
        CorDictionaryDTO result = corDictionaryMapper.DB2DTO(corDictionary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("CorDictionary", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CorDictionaryDTO> createDictionaryValueUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                           @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                           @ApiParam(value = "corDictionaryDTO", required = true) @Valid @RequestBody CorDictionaryDTO corDictionaryDTO) throws URISyntaxException {
        log.debug("REST request to save CorDictionary : {}", corDictionaryDTO);
        if (corDictionaryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CorDictionary", "idexists", "A new CorDictionary cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorDictionary corDictionary = corDictionaryMapper.DTO2DB(corDictionaryDTO, corNetwork, new CorModule().name(module), new CorDictionaryType().name(type));
        corDictionary = corDictionaryRepository.save(corDictionary);
        CorDictionaryDTO result = corDictionaryMapper.DB2DTO(corDictionary);

        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/configuration/network/dictionary/" + module + "/" + type + "/" + result.getId())).body(result);
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
    public ResponseEntity<List<CorDictionaryDTO>> getAllDictionaryValueUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                                @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                                @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get CorDictionary : {}", networkShortcut);

        List<CorDictionary> corDictionaries = corDictionaryRepository.findByCorDictionaryTypeAndCorModuleAndNetwork_Shortcut(type, module, networkShortcut);
        List<CorDictionaryDTO> dictionaryPTS = corDictionaryMapper.DBs2DTOs(corDictionaries);
        return Optional.ofNullable(dictionaryPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CorDictionaryDTO> getDictionaryValueGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                  @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorDictionary : {}", networkShortcut);


        CorDictionary corDictionary = corDictionaryRepository.findByIdAndCorDictionaryTypeAndCorModuleAndNetwork_Shortcut(id, type, module, networkShortcut);
        CorDictionaryDTO corDictionaryDTO = corDictionaryMapper.DB2DTO(corDictionary);
        return Optional.ofNullable(corDictionaryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
