package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibItemPT;
import io.protone.domain.LIBMediaItem;
import io.protone.domain.enumeration.LIBItemStateEnum;
import io.protone.domain.enumeration.LIBItemTypeEnum;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomItemMapperExt {

    @Inject
    CustomLIBAlbumMapper albumMapper;

    @Inject
    CustomLIBArtistMapper artistMapper;

    @Inject
    CustomCORPersonMapperExt personMapper;

    @Inject
    CustomLIBLabelMapperExt labelMapper;

    @Inject
    CustomLIBLibraryMapperExt libraryMapper;

    @Inject
    CustomLIBMarkerMapperExt markerMapper;

    @Inject
    CustomLIBTrackMapperExt trackMapper;

    public LibItemPT DB2DTO(LIBMediaItem db) {

        if (db == null )
            return null;

        LibItemPT dto = new LibItemPT()
            .idx(db.getIdx())
            .length(db.getLength().intValue())
            .name(db.getName())
            .resourceType(mapResourceType(db.getItemType()))
            .state(mapState(db.getState()))
            .label(labelMapper.DB2DTO(db.getLabel()))
            .album(albumMapper.DB2DTO(db.getAlbum()))
            .artist(artistMapper.DB2DTO(db.getArtist()))
            .library(libraryMapper.DB2DTO(db.getLibrary()))
            .track(trackMapper.DB2DTO(db.getTrack()))
            ;

        dto.author(null).composer(null); //add author and composer associacions
        dto.markers(null); //add markers

        return dto;
    }

    private LibItemPT.StateEnum mapState(LIBItemStateEnum state) {

        if (state.compareTo(LIBItemStateEnum.IS_ARCHIVED) == 0)
            return LibItemPT.StateEnum.ARCHIVED;
        else if (state.compareTo(LIBItemStateEnum.IS_DELETED) == 0)
            return LibItemPT.StateEnum.DELETED;
        else if (state.compareTo(LIBItemStateEnum.IS_DISABLED) == 0)
            return LibItemPT.StateEnum.DISABLED;
        else if (state.compareTo(LIBItemStateEnum.IS_ENABLED) == 0)
            return LibItemPT.StateEnum.ENABLED;
        else if (state.compareTo(LIBItemStateEnum.IS_ERROR) == 0)
            return LibItemPT.StateEnum.ERROR;
        else if (state.compareTo(LIBItemStateEnum.IS_NEW) == 0)
            return LibItemPT.StateEnum.NEW;
        else if (state.compareTo(LIBItemStateEnum.IS_POSTPROCESS) == 0)
            return LibItemPT.StateEnum.POSTPROCESS;
        else
            return LibItemPT.StateEnum.OTHER;
    }

    private LibItemPT.ResourceTypeEnum mapResourceType(LIBItemTypeEnum itemType) {

        if (itemType.compareTo(LIBItemTypeEnum.IT_AUDIO) == 0)
            return LibItemPT.ResourceTypeEnum.AUDIO;
        else if (itemType.compareTo(LIBItemTypeEnum.IT_VIDEO) == 0)
            return LibItemPT.ResourceTypeEnum.VIDEO;
        else if (itemType.compareTo(LIBItemTypeEnum.IT_COMMAND) == 0)
            return LibItemPT.ResourceTypeEnum.COMMAND;
        else
            return LibItemPT.ResourceTypeEnum.OTHER;
    }


    public List<LibItemPT> DBs2DTOs(List<LIBMediaItem> dbs) {

        if ( dbs == null )
            return null;

        List<LibItemPT> dtos = new ArrayList<>();
        for (LIBMediaItem item : dbs )
            dtos.add( DB2DTO(item));

        return dtos;
    }

    public LIBMediaItem DTO2DB(LibItemPT dto) {

        if (dto == null)
            return null;

        LIBMediaItem db = new LIBMediaItem()
            .idx(dto.getIdx())
            .length(dto.getLength().longValue())
            .name(dto.getName())
            .itemType(mapItemType(dto.getResourceType()))
            .state(LIBItemStateEnum.valueOf(dto.getState().toString()))
            .label(labelMapper.DTO2DB(dto.getLabel()))
            .album(albumMapper.DTO2DB(dto.getAlbum()))
            .artist(artistMapper.DTO2DB(dto.getArtist()))
            .library(libraryMapper.DTO2DB(dto.getLibrary()))
            .track(trackMapper.DTO2DB(dto.getTrack()));

        //add author and composer associacions
        //add markers

        return db;
    }

    public LIBItemTypeEnum mapItemType(LibItemPT.ResourceTypeEnum type) {

        if (type.compareTo(LibItemPT.ResourceTypeEnum.AUDIO) == 0)
            return LIBItemTypeEnum.IT_AUDIO;
        else if (type.compareTo(LibItemPT.ResourceTypeEnum.VIDEO) == 0)
            return LIBItemTypeEnum.IT_VIDEO;
        else if (type.compareTo(LibItemPT.ResourceTypeEnum.COMMAND) == 0)
            return LIBItemTypeEnum.IT_COMMAND;
        else
            return LIBItemTypeEnum.IT_OTHER;
    }

    public List<LIBMediaItem> DTOs2DBs(List<LibItemPT> dtos) {

        if (dtos == null)
            return null;

        List<LIBMediaItem> dbs = new ArrayList<>();
        for (LibItemPT item : dtos )
            dbs.add( DTO2DB(item));

        return dbs;
    }
}
