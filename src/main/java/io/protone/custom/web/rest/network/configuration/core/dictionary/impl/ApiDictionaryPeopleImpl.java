package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.ConfPersonPT;
import io.protone.custom.service.mapper.CustomCORPersonMapper;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryPeople;
import io.protone.domain.CORPerson;
import io.protone.repository.CCORNetworkRepository;
import io.protone.repository.CORPersonRepository;
import io.protone.service.dto.CORPersonDTO;
import io.protone.service.mapper.CORPersonMapper;
import io.protone.web.rest.CORPersonResource;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiDictionaryPeopleImpl implements ApiDictionaryPeople {
    private final Logger log = LoggerFactory.getLogger(ApiDictionaryPeopleImpl.class);

    @Inject
    private CORPersonRepository cORPersonRepository;

    @Inject
    private NetworkService networkService;

    @Inject
    private CustomCORPersonMapper customCORPersonMapper;

    @Override
    public ResponseEntity<ConfPersonPT> updatePersonUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "personDTO", required = true) @RequestBody ConfPersonPT personDTO) {
        log.debug("REST request to update CORPerson : {}", personDTO);
        if (personDTO.getId() == null) {
            return createPersonUsingPOST(networkShortcut, personDTO);
        }
        CORPerson cORPerson = customCORPersonMapper.cORPersonDTOToCORPerson(personDTO);
        cORPerson = cORPersonRepository.save(cORPerson);
        ConfPersonPT result = customCORPersonMapper.cORPersonToCORPersonDTO(cORPerson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORPerson", personDTO.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfPersonPT> createPersonUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "personDTO", required = true) @RequestBody ConfPersonPT personDTO) {
        log.debug("REST request to save CORPerson : {}", personDTO);
        if (personDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORPerson", "idexists", "A new cORPerson cannot already have an ID")).body(null);
        }
        CORPerson cORPerson = customCORPersonMapper.cORPersonDTOToCORPerson(personDTO);
        cORPerson = cORPersonRepository.save(cORPerson);
        ConfPersonPT result = customCORPersonMapper.cORPersonToCORPersonDTO(cORPerson);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deletePersonUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CORPerson : {}", id);
        cORPersonRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORPerson", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<ConfPersonPT>> getAllPeopleUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CORPeople");
        List<CORPerson> cORPeople = cORPersonRepository.findAll();
        return ResponseEntity.ok().body(customCORPersonMapper.cORPeopleToCORPersonDTOs(cORPeople));
    }

    @Override
    public ResponseEntity<ConfPersonPT> getPersonUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CORPerson : {}", id);
        CORPerson cORPerson = cORPersonRepository.findOne(id);
        ConfPersonPT cORPersonDTO = customCORPersonMapper.cORPersonToCORPersonDTO(cORPerson);
        return Optional.ofNullable(cORPersonDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
