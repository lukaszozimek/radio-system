package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.repository.cor.CorPersonRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.ConfPersonPT;
import io.protone.web.rest.mapper.CorPersonMapper;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryPeople;
import io.protone.domain.CorPerson;
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
public class ApiDictionaryPeopleImpl implements ApiDictionaryPeople {
    private final Logger log = LoggerFactory.getLogger(ApiDictionaryPeopleImpl.class);

    @Inject
    private CorPersonRepository corPersonRepository;

    @Inject
    private CorNetworkService networkService;

    @Inject
    private CorPersonMapper corPersonMapper;

    @Override
    public ResponseEntity<ConfPersonPT> updatePersonUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "personDTO", required = true)@Valid @RequestBody ConfPersonPT personDTO) throws URISyntaxException {
        log.debug("REST request to update CorPerson : {}", personDTO);
        if (personDTO.getId() == null) {
            return createPersonUsingPOST(networkShortcut, personDTO);
        }
        CorPerson cORPerson = corPersonMapper.DTO2DB(personDTO);
        cORPerson = corPersonRepository.save(cORPerson);
        ConfPersonPT result = corPersonMapper.DB2DTO(cORPerson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORPerson", personDTO.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfPersonPT> createPersonUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "personDTO", required = true)@Valid @RequestBody ConfPersonPT personDTO) throws URISyntaxException {
        log.debug("REST request to save CorPerson : {}", personDTO);
        if (personDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORPerson", "idexists", "A new cORPerson cannot already have an ID")).body(null);
        }
        CorPerson cORPerson = corPersonMapper.DTO2DB(personDTO);
        cORPerson = corPersonRepository.save(cORPerson);
        ConfPersonPT result = corPersonMapper.DB2DTO(cORPerson);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/configuration/network/dictionary/country/" + result.getId()))
            .body(result);
    }

    @Override
    public ResponseEntity<Void> deletePersonUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true)
    @PathVariable("id") Long id) {
        log.debug("REST request to delete CorPerson : {}", id);
        corPersonRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORPerson", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<ConfPersonPT>> getAllPeopleUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CORPeople");
        List<CorPerson> cORPeople = corPersonRepository.findAll();
        return ResponseEntity.ok().body(corPersonMapper.DBs2DTOs(cORPeople));
    }

    @Override
    public ResponseEntity<ConfPersonPT> getPersonUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorPerson : {}", id);
        CorPerson cORPerson = corPersonRepository.findOne(id);
        ConfPersonPT cORPersonDTO = corPersonMapper.DB2DTO(cORPerson);
        return Optional.ofNullable(cORPersonDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
