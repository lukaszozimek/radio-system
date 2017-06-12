package io.protone.service.traffic.base;

import io.protone.domain.*;
import io.protone.domain.enumeration.CorDayOfWeekEnum;
import io.protone.repository.cor.CorChannelRepository;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.library.LibLibraryRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.repository.traffic.TraBlockConfigurationRepository;
import io.protone.repository.traffic.TraBlockRepository;
import io.protone.repository.traffic.TraEmissionRepository;
import io.protone.repository.traffic.TraPlaylistRepository;
import io.protone.service.traffic.TraAdvertisementService;
import io.protone.service.traffic.TraPlaylistService;
import io.protone.web.rest.dto.traffic.TraAdvertisementDTO;
import io.protone.web.rest.mapper.TraAdvertisementMapper;
import org.assertj.core.util.Lists;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lukaszozimek on 06/06/2017.
 */
public class TraPlaylistBasedTest {
    private static final int COMMERCIALS_AUDIO_FILES_NUMBER_IN_PLAYLIST_SCHEDULE = 300;

    private static final int DAILY_BLOCK_NUMBER = 96;
    @Inject
    private TraPlaylistService traPlaylistService;

    @Inject
    private TraAdvertisementService traAdvertisementService;

    @Inject
    private LibMediaItemRepository libMediaItemRepository;

    @Inject
    private CorChannelRepository corChannelRepository;

    @Inject
    private CorNetworkRepository corNetworkRepository;

    @Inject
    private TraBlockRepository trablockRepository;

    @Inject
    private TraPlaylistRepository traPlaylistRepository;

    @Inject
    private TraEmissionRepository traEmissionRepository;

    @Inject
    private LibLibraryRepository libLibraryRepository;

    @Inject
    private CrmAccountRepository crmAccountRepository;

    @Inject
    private TraAdvertisementMapper traAdvertisementMapper;

    protected PodamFactory factory = new PodamFactoryImpl();

    protected CorNetwork corNetwork;

    protected CorChannel corChannel;

    protected LibLibrary libLibrary;

    protected TraAdvertisementDTO advertisementToShuffleDTO;

    protected TraAdvertisement advertisementToShuffle;

    protected LibMediaItem libMediaItemToShuffle;

    protected CrmAccount crmAccount;

    protected List<LibMediaItem> mediaItemList;

    protected List<TraAdvertisement> advertisements;

    protected List<TraPlaylist> traPlaylists;

    @Inject
    protected TraBlockConfigurationRepository trablockConfigurationRepository;

    protected List<LibMediaItem> buildMediaItems() throws InterruptedException {
        List<LibMediaItem> libMediaItems = Lists.newArrayList();
        for (int i = 0; i < COMMERCIALS_AUDIO_FILES_NUMBER_IN_PLAYLIST_SCHEDULE; i++) {
            Thread.sleep(1);
            LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
            libMediaItem.setIdx(String.valueOf(java.util.concurrent.ThreadLocalRandom.current().nextInt()));
            libMediaItem.length(java.util.concurrent.ThreadLocalRandom.current().nextDouble(30000.0));
            libMediaItem.network(corNetwork);
            libMediaItem.setLibrary(libLibrary);
            libMediaItems.add(libMediaItemRepository.saveAndFlush(libMediaItem));
        }
        return libMediaItems;
    }

    protected List<TraAdvertisement> buildAdvertisments() {
        List<TraAdvertisement> traAdvertisements = Lists.newArrayList();
        mediaItemList.stream().forEach(libMediaItem -> {
            TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
            traAdvertisement.mediaItem(libMediaItem);
            traAdvertisement.network(corNetwork);
            traAdvertisement.setCustomer(crmAccount);
            traAdvertisements.add(traAdvertisementService.saveAdvertisement(traAdvertisement));

        });
        return traAdvertisements;
    }

    protected Set<TraBlock> buildDayliBlockStrucutre() {

        Set<TraBlock> traBlocks = new HashSet<>();
        for (int i = 1; i < DAILY_BLOCK_NUMBER; i++) {
            TraBlock trablock = factory.manufacturePojo(TraBlock.class);
            trablock.sequence(i);
            trablock.setLength(Long.valueOf((3 * 60000)));
            trablock.setChannel(corChannel);
            trablock.setNetwork(corNetwork);
            trablock.emissions(buildTraEmissionss(java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 5), 5, trablock.getLength()));
            trablock = trablockRepository.saveAndFlush(trablock);
            traBlocks.add(trablock);
        }
        return traBlocks;
    }


    protected Set<TraBlockConfiguration> buildBlockConfiguration(CorDayOfWeekEnum corDayOfWeekEnum) {
        Set<TraBlockConfiguration> traBlocks = new HashSet<>();
        for (int hour = 0; hour < 24; hour++) {
            for (int blockInHour = 0; blockInHour < 3; blockInHour++) {
                TraBlockConfiguration trablock = factory.manufacturePojo(TraBlockConfiguration.class);
                trablock.sequence(hour + blockInHour);
                trablock.setLength(Long.valueOf((3 * 60000)));
                if (blockInHour > 0) {
                    trablock.setStartBlock(LocalTime.of(hour, blockInHour * 15).toNanoOfDay());

                } else {
                    trablock.setStartBlock(LocalTime.of(hour, 0).toNanoOfDay());
                }
                trablock.setStopBlock(trablock.getStartBlock().longValue() + trablock.getLength());
                trablock.setChannel(corChannel);
                trablock.setNetwork(corNetwork);
                trablock.setDay(corDayOfWeekEnum);
                trablock = trablockConfigurationRepository.saveAndFlush(trablock);
                traBlocks.add(trablock);
            }
        }
        return traBlocks;
    }

    protected Set<TraEmission> buildTraEmissionss(int commercialsInBlock, int maxCommercialNumber, Long blockLenght) {
        Long currentLenght = blockLenght;
        Set<TraEmission> traEmissions = new HashSet<>();
        for (int i = 1; i < commercialsInBlock; i++) {
            TraEmission traEmission = factory.manufacturePojo(TraEmission.class);
            traEmission.advertiment(advertisements.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(0, COMMERCIALS_AUDIO_FILES_NUMBER_IN_PLAYLIST_SCHEDULE)));
            traEmission.sequence(java.util.concurrent.ThreadLocalRandom.current().nextInt(0, maxCommercialNumber));
            traEmission.setChannel(corChannel);
            traEmission.setNetwork(corNetwork);
            if (0 < (currentLenght - traEmission.getAdvertiment().getMediaItem().getLength().longValue())) {
                traEmission = traEmissionRepository.saveAndFlush(traEmission);
                currentLenght = currentLenght - traEmission.getAdvertiment().getMediaItem().getLength().longValue();
                traEmissions.add(traEmission);
            }
        }
        return traEmissions;
    }

    protected TraPlaylist buildTraPlaylist(LocalDate date) {
        TraPlaylist traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist.playlists(buildDayliBlockStrucutre());
        traPlaylist.setPlaylistDate(date);
        traPlaylist.setChannel(corChannel);
        traPlaylist.setNetwork(corNetwork);
        traPlaylist = traPlaylistRepository.saveAndFlush(traPlaylist);
        return traPlaylist;
    }

    protected void buildMustHavePojos() {
        corChannel = factory.manufacturePojo(CorChannel.class);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        libLibrary = factory.manufacturePojo(LibLibrary.class);
        crmAccount = factory.manufacturePojo(CrmAccount.class);
        libMediaItemToShuffle = factory.manufacturePojo(LibMediaItem.class);
        advertisementToShuffle = factory.manufacturePojo(TraAdvertisement.class);
        corNetwork.setId(12L);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
        corChannel.setId(12L);
        corChannel.setNetwork(corNetwork);
        corChannel = corChannelRepository.saveAndFlush(corChannel);
        libLibrary.setId(12L);
        libLibrary.network(corNetwork);
        libLibrary = libLibraryRepository.saveAndFlush(libLibrary);

        crmAccount.setNetwork(corNetwork);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);

        libMediaItemToShuffle.setNetwork(corNetwork);
        libMediaItemToShuffle.setLibrary(libLibrary);
        libMediaItemToShuffle = libMediaItemRepository.saveAndFlush(libMediaItemToShuffle);

        advertisementToShuffle.setCustomer(crmAccount);
        advertisementToShuffle.setNetwork(corNetwork);
        advertisementToShuffle.setMediaItem(libMediaItemToShuffle);
        advertisementToShuffle = traAdvertisementService.saveAdvertisement(advertisementToShuffle);
        advertisementToShuffleDTO = traAdvertisementMapper.DB2DTO(advertisementToShuffle);

    }

    protected void buildBlockConfiguration() {

        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_MONDAY));
        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_TUESDAY));
        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_WEDNESDAY));
        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_THURSDAY));
        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_FRIDAY));
        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_SATURDAY));
        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_SUNDAY));
    }
}
