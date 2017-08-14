package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.repository.SchClockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lukaszozimek on 14/08/2017.
 */
@Service
@Transactional
public class SchClockService {

    @Autowired
    private SchClockRepository schClockRepository;


    public List<SchClock> findAllByNetworkShortcutAndChannelShortcut(String networkShortcut, String channelShortcut, Pageable pageable) {
        return schClockRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pageable);
    }


}
