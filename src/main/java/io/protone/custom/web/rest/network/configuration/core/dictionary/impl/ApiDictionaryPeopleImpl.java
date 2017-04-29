package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.ConfPersonPT;
import io.protone.web.rest.mapper.CorPersonMapper;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryPeople;
import io.protone.domain.CorPerson;
import io.protone.repository.CorPersonRepository;
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
public class ApiDictionaryPeopleImpl implements ApiDictionaryPeople {
    private final Logger log = LoggerFactory.getLogger(ApiDictionaryPeopleImpl.class);

    @Inject
    private CorPersonRepository cORPersonRepository;

    @Inject
    private CorNetworkService networkService;

    @Inject
    private CorPersonMapper customCorPersonMapper;

    @Override
    public ResponseEntity<ConfPersonPT> updatePersonUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "personDTO", required = true) @RequestBody ConfPersonPT personDTO) {
        log.debug("REST request to update CorPerson : {}", personDTO);
        if (personDTO.getId() == null) {
            return createPersonUsingPOST(networkShortcut, personDTO);
        }
        CorPerson cORPerson = customCorPersonMapper.DTO2DB(personDTO);
        cORPerson = cORPersonRepository.save(cORPerson);
        ConfPersonPT result = customCorPersonMapper.DB2DTO(cORPerson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORPerson", personDTO.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfPersonPT> createPersonUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "personDTO", required = true) @RequestBody ConfPersonPT personDTO) {
        log.debug("REST request to save CorPerson : {}", personDTO);
        if (personDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORPerson", "idexists", "A new cORPerson cannot already have an ID")).body(null);
        }
        CorPerson cORPerson = customCorPersonMapper.DTO2DB(personDTO);
        cORPerson = cORPersonRepository.save(cORPerson);
        ConfPersonPT result = customCorPersonMapper.DB2DTO(cORPerson);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deletePersonUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true)
    @PathVariable("id") Long id) {
        log.debug("REST request to delete CorPerson : {}", id);
        cORPersonRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORPerson", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<ConfPersonPT>> getAllPeopleUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CORPeople");
        List<CorPerson> cORPeople = cORPersonRepository.findAll();
        return ResponseEntity.ok().body(customCorPersonMapper.DBs2DTOs(cORPeople));
    }

    @Override
    public ResponseEntity<ConfPersonPT> getPersonUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorPerson : {}", id);
        CorPerson cORPerson = cORPersonRepository.findOne(id);
        ConfPersonPT cORPersonDTO = customCorPersonMapper.DB2DTO(cORPerson);
        return Optional.ofNullable(cORPersonDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
