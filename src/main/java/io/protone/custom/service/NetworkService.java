package io.protone.custom.service;

import io.protone.domain.CorNetwork;
import io.protone.repository.CorNetworkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 16.01.2017.
 */
@Service
public class NetworkService {

    @Inject
    private CorNetworkRepository ccorNetworkRepository;

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
        return ccorNetworkRepository.save(network);
    }

}
