package io.protone.custom.service;

import io.protone.domain.CORNetwork;
import io.protone.repository.CCORNetworkRepository;
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
    CCORNetworkRepository ccorNetworkRepository;

    public List<CORNetwork> findAllNetworks() {
        return ccorNetworkRepository.findAll();
    }

    public CORNetwork findNetwork(String shortcut) {
        return ccorNetworkRepository.findByShortcut(shortcut);
    }

    @Transactional
    public void deleteNetwork(String shortcut) {
        ccorNetworkRepository.deleteByShortcut(shortcut);
    }

    public CORNetwork save(CORNetwork network) {
        return ccorNetworkRepository.save(network);
    }
}
