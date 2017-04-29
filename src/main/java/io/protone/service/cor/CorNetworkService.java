package io.protone.service.cor;

import io.protone.domain.CorNetwork;
import io.protone.repository.custom.CustomCorNetworkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 16.01.2017.
 */
@Service
public class CorNetworkService {
    private final Logger log = LoggerFactory.getLogger(CorNetworkService.class);

    @Inject
    private CustomCorNetworkRepository ccorNetworkRepository;

    public List<CorNetwork> findAllNetworks() {
        return ccorNetworkRepository.findAll();
    }

    public CorNetwork findNetwork(String shortcut) {
        return ccorNetworkRepository.findOneByShortcut(shortcut);
    }

    @Transactional
    public void deleteNetwork(String shortcut) {
        ccorNetworkRepository.deleteByShortcut(shortcut);
    }

    public CorNetwork save(CorNetwork network) {
        log.debug("Persisting CorNetwork: {}", network);
        return ccorNetworkRepository.save(network);
    }

}
