package io.protone.application.service.language.pql;

import com.google.common.collect.Lists;
import io.jsonwebtoken.lang.Assert;
import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorChannelDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.enumeration.CorEntityTypeEnum;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.language.service.pql.LaPQLMappingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static io.protone.core.domain.enumeration.CorEntityTypeEnum.Channel;

/**
 * Created by lukaszozimek on 19.07.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LaPQLMappingServiceTest {
    private static final String SAMPLE = "sample";
    private static final String SAMPLE_SHORTCUT = "sam";
    private static final String SAMPLE_NET = "net";


    @Autowired
    private LaPQLMappingService pqlMappingService;

    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Test
    public void shouldReturnCorChannelWithPredicateName() throws Exception {
        //when
        CorNetwork corNetwork = new CorNetwork().shortcut(SAMPLE_NET).name(SAMPLE_NET);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
        CorChannel corChannel = new CorChannel().shortcut(SAMPLE_SHORTCUT).name(SAMPLE).network(corNetwork);
        corChannelRepository.saveAndFlush(corChannel);

        List corChannels = Lists.newArrayList(corChannel);
        List mappedChannel = pqlMappingService.DBs2DTOs(corChannels, Channel);
        Assert.notNull(mappedChannel);
        Assert.notEmpty(mappedChannel);
        Assert.isTrue(mappedChannel.get(0).getClass().equals(CorChannelDTO.class));
    }


}
