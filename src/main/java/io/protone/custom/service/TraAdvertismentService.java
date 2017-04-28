package io.protone.custom.service;

import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.service.mapper.CorDictionaryMapper;
import io.protone.service.mapper.CrmAccountMapper;
import io.protone.service.mapper.TraAdvertismentMapper;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraAdvertisement;
import io.protone.repository.LibMediaItemRepository;
import io.protone.repository.custom.CustomCrmAccountRepositoryEx;
import io.protone.repository.custom.CustomTraAdvertisementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TraAdvertismentService {

    private final Logger log = LoggerFactory.getLogger(TraAdvertismentService.class);

    @Inject
    private TraAdvertismentMapper traAdvertismentMapper;

    @Inject
    private CustomTraAdvertisementRepository traAdvertisementRepository;

    @Inject
    private CorDictionaryMapper corDictionaryMapper;

    @Inject
    private CrmAccountMapper customCrmAccountMapper;

    @Inject
    private LibMediaItemRepository libMediaItemRepository;


    @Inject
    private CustomCrmAccountRepositoryEx crmAccountRepository;

    public List<TraAdvertisementPT> getAllAdvertisement(CorNetwork corNetwork) {
        return traAdvertisementRepository.findByNetwork(corNetwork).stream().map(traAdvertisement -> traAdvertismentMapper.DB2DTO(traAdvertisement)).collect(toList());
    }

    public TraAdvertisementPT saveAdvertisement(TraAdvertisementPT traAdvertisementPT, CorNetwork corNetwork) {
        TraAdvertisement traAdvertisement = traAdvertismentMapper.DTO2DB(traAdvertisementPT);
        traAdvertisement.setNetwork(corNetwork);
        log.debug("Persisting TraAdvertisement: {}", traAdvertisement);
        traAdvertisementRepository.save(traAdvertisement);
        return traAdvertismentMapper.DB2DTO(traAdvertisement);
    }

    public void deleteAdvertisement(Long idx, CorNetwork corNetwork) {
        TraAdvertisement traAdvertisement = traAdvertisementRepository.findOne(idx);
        libMediaItemRepository.delete(traAdvertisement.getMediaItem());
        traAdvertisementRepository.delete(traAdvertisement);
    }

    public TraAdvertisementPT getAdvertisement(Long id, CorNetwork corNetwork) {
        TraAdvertisement traAdvertisement = traAdvertisementRepository.findOne(id);
        return traAdvertismentMapper.DB2DTO(traAdvertisement);
    }


    public List<TraAdvertisementPT> getCustomerAdvertisments(String shortcut, CorNetwork corNetwork) {
        CrmAccount crmAccount = crmAccountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return traAdvertisementRepository.findByCustomerAndNetwork(crmAccount, corNetwork).stream().map(traAdvertisment -> traAdvertismentMapper.DB2DTO(traAdvertisment)).collect(toList());
    }


}
