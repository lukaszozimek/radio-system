package io.protone.application.service.library.util;

import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.library.domain.LibAudioObject;
import io.protone.library.domain.LibCloudObject;
import io.protone.library.domain.LibLibrary;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.repository.LibAudioObjectRepository;
import io.protone.library.repository.LibCloudObjectRepository;
import io.protone.library.repository.LibLibraryRepository;
import io.protone.library.repository.LibMediaItemRepository;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;

/**
 * Created by lukaszozimek on 14/08/2017.
 */

public class LibraryGenerator {


    @Autowired
    protected CorNetworkRepository corNetworkRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Autowired
    private LibLibraryRepository libLibraryRepository;

    @Autowired
    private LibAudioObjectRepository audioObjectRepository;

    @Autowired
    private LibCloudObjectRepository cloudObjectRepository;


    protected CorNetwork corNetwork;

    protected CorChannel corChannel;

    protected PodamFactory factory;

    protected LibLibrary libLibrary;


    public void initializeLibarary() {
        initializeRequiredEntities();
        if (libLibrary == null) {
            initializeLibaryEntity();
        }
    }

    public List<LibMediaItem> generateFullItemListWithLenghtInRange(double lenghtMin, double lenghtMax, Long librarySize) throws InterruptedException {
        List<LibMediaItem> libMediaItems = Lists.newArrayList();
        for (long i = 0; i < librarySize; i++) {
            LibCloudObject cloudObject = generateCloudObject();
            LibMediaItem libMediaItem = generateLibMediaItem(java.util.concurrent.ThreadLocalRandom.current().nextDouble(lenghtMin, lenghtMax));
            LibAudioObject libAudioObject = generateAudioObject(cloudObject, libMediaItem);
            libMediaItems.add(libMediaItem);
            Thread.sleep(1L);
        }
        return libMediaItems;
    }

    private LibCloudObject generateCloudObject() {
        LibCloudObject libCloudObject = factory.manufacturePojo(LibCloudObject.class);
        libCloudObject.setNetwork(corNetwork);
        libCloudObject = cloudObjectRepository.saveAndFlush(libCloudObject);
        return libCloudObject;
    }

    private LibAudioObject generateAudioObject(LibCloudObject cloudObject, LibMediaItem libMediaItem) {
        LibAudioObject libAudioObject = factory.manufacturePojo(LibAudioObject.class);
        libAudioObject.setMediaItem(libMediaItem);
        libAudioObject.setCloudObject(cloudObject);
        libAudioObject.setNetwork(corNetwork);
        libAudioObject = audioObjectRepository.saveAndFlush(libAudioObject);
        return libAudioObject;
    }

    private LibMediaItem generateLibMediaItem(double lenght) {
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setId(null);
        libMediaItem.length(lenght);
        libMediaItem.setLibrary(libLibrary);
        libMediaItem.setNetwork(corNetwork);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);
        return libMediaItem;

    }

    private void initializeRequiredEntities() {
        factory = new PodamFactoryImpl();
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
    }

    private void initializeLibaryEntity() {
        libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setId(null);
        libLibrary.setNetwork(corNetwork);
        this.libLibrary = libLibraryRepository.saveAndFlush(libLibrary);
    }

}
