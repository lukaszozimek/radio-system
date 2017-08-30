package io.protone.application.service.scheduler.base;

import com.google.common.collect.Sets;
import io.protone.application.service.library.util.LibraryGenerator;
import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.domain.SchAttachment;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchEventEmission;
import io.protone.scheduler.domain.SchEventEmissionAttachment;

import java.util.List;

/**
 * Created by lukaszozimek on 14/08/2017.
 */
public class SchedulerBaseTest extends LibraryGenerator {

    private List<LibMediaItem> libMediaItemList;

    public void setUp() throws Exception {
        initializeLibarary();
        libMediaItemList = generateFullItemListWithLenghtInRange(30000, 500000, 10L);

    }

    protected SchEmission buildEmissionForBlock() {
        return new SchEmission().mediaItem(libMediaItemList.get(0)).channel(corChannel).network(corNetwork);
    }

    protected SchEmission buildEmissionForWithAttachment() {
        return new SchEmission().mediaItem(libMediaItemList.get(0)).channel(corChannel).network(corNetwork).attachments(Sets.newHashSet(new SchAttachment().channel(corChannel).network(corNetwork).mediaItem(libMediaItemList.get(2))));
    }

    protected SchEventEmission buildSchEventEmissionForSchEvent() {
        return new SchEventEmission().mediaItem(libMediaItemList.get(0)).channel(corChannel).network(corNetwork);
    }

    protected SchEventEmission buildSchEventEmissionForSchEventWithAttachment() {
        return new SchEventEmission().mediaItem(libMediaItemList.get(0)).channel(corChannel).network(corNetwork).attachments(Sets.newHashSet(new SchEventEmissionAttachment().channel(corChannel).network(corNetwork).mediaItem(libMediaItemList.get(2))));
    }
}
