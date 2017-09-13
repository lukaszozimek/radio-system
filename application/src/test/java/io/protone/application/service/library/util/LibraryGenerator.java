package io.protone.application.service.library.util;

import com.google.common.collect.Sets;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.library.domain.*;
import io.protone.library.repository.*;
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
    protected CorNetwork corNetwork;
    protected CorChannel corChannel;
    protected PodamFactory factory;
    protected LibMediaLibrary libMediaLibrary;
    protected LibFileLibrary libFileLibrary;
    @Autowired
    private LibMediaItemRepository libMediaItemRepository;
    @Autowired
    private LibLibraryRepository libLibraryRepository;
    @Autowired
    private LibFileLibraryRepository libFileLibraryRepository;
    @Autowired
    private LibAudioObjectRepository audioObjectRepository;
    @Autowired
    private LibCloudObjectRepository cloudObjectRepository;

    public void initializeLibarary() {
        initializeRequiredEntities();
        if (libMediaLibrary == null) {
            initializeLibaryEntity();
        }
    }

    private void initializeRequiredEntities() {
        factory = new PodamFactoryImpl();
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
    }

    private void initializeLibaryEntity() {
        libMediaLibrary = new LibMediaLibrary();
        libMediaLibrary.setId(null);
        libMediaLibrary.setCounter(0L);
        libMediaLibrary.setPrefix("v");
        libMediaLibrary.shortcut("100");
        libMediaLibrary.setName("Testowa Bliblioteka Schedulera");
        libMediaLibrary.setNetwork(corNetwork);
        this.libMediaLibrary = libLibraryRepository.saveAndFlush(libMediaLibrary);

        libFileLibrary = new LibFileLibrary();
        libFileLibrary.setId(null);
        libFileLibrary.setCounter(0L);
        libFileLibrary.setPrefix("v");
        libFileLibrary.shortcut("100");
        libFileLibrary.setName("Testowa Bliblioteka Schedulera");
        libFileLibrary.setNetwork(corNetwork);
        libFileLibrary.setChannels(Sets.newHashSet(corChannel));
        this.libFileLibrary = this.libFileLibraryRepository.saveAndFlush(libFileLibrary);

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
        libMediaItem.setLibrary(libMediaLibrary);
        libMediaItem.setNetwork(corNetwork);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);
        return libMediaItem;

    }


}
