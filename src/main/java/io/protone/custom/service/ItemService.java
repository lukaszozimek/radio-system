package io.protone.custom.service;

import io.protone.config.s3.S3Client;
import io.protone.config.s3.exceptions.DownloadException;
import io.protone.config.s3.exceptions.MediaResourceException;
import io.protone.config.s3.exceptions.S3Exception;
import io.protone.config.s3.exceptions.UploadException;
import io.protone.custom.consts.ServiceConstants;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.mapper.CustomItemMapperExt;
import io.protone.custom.utils.MediaUtils;
import io.protone.domain.*;
import io.protone.domain.enumeration.LIBAudioQualityEnum;
import io.protone.domain.enumeration.LIBItemStateEnum;
import io.protone.domain.enumeration.LIBItemTypeEnum;
import io.protone.repository.*;
import io.protone.security.SecurityUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ItemService {

    @Inject
    S3Client s3Client;

    @Inject
    LibraryService libraryService;

    @Inject
    LIBMediaItemRepositoryEx itemRepository;

    @Inject
    CustomItemMapperExt itemMapper;

    @Inject
    private UserRepository userRepository;

    @Inject
    LIBCloudObjectRepository cloudObjectRepository;

    @Inject
    MediaUtils mediaUtils;

    @Inject
    LIBMediaItemRepositoryEx mediaItemRepository;

    @Inject
    LIBAudioObjectRepositoryEx audioObjectRepository;

    public LibItemPT getItem(String networkShortcut, String libraryShortcut, String idx) {
        LibItemPT result = null;
        LIBLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return result;

        LIBMediaItem itemDB = itemRepository.findByLibraryAndIdx(libraryDB, idx).get();
        result = itemMapper.DB2DTO(itemDB);
        return result;
    }

    public List<LibItemPT> getItem(String networkShortcut, String libraryShortcut) {
        List<LibItemPT> results = new ArrayList<>();
        LIBLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return results;

        List<LIBMediaItem> itemsDB = itemRepository.findByLibrary(libraryDB);
        results = itemMapper.DBs2DTOs(itemsDB);
        return results;
    }

    @Transactional
    public void deleteItem(String networkShortcut, String libraryShortcut, String idx) {
        LibItemPT dto = getItem(networkShortcut, libraryShortcut, idx);
        LIBMediaItem itemToDelete = itemMapper.DTO2DB(dto);
        if (itemToDelete != null) {
            itemRepository.delete(itemToDelete);
            itemRepository.flush();
        }
    }


    public List<LibItemPT> upload(String networkShortcut, String libraryShortcut, MultipartFile[] files) throws IOException, MediaResourceException {

        List<LibItemPT> result = new ArrayList<>();

        if (files == null || files.length == 0)
            return result;

        LIBLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return result;

        for (MultipartFile file : files) {

            String fileName = file.getOriginalFilename();

            //FIX THIS ASAP!!!
            //Path filePath = Paths.get(String.format("%s%s", File.separator, fileName));
            //String contentType = Files.probeContentType(filePath);
            String contentType = "application/octet-stream";

            String fileUUID = UUID.randomUUID().toString();

            ByteArrayInputStream bais = null;
            bais = new ByteArrayInputStream(file.getBytes());

            try {
                s3Client.upload(fileUUID, bais, contentType);

                LIBCloudObject cloudObject = new LIBCloudObject();

                cloudObject.setUuid(fileUUID);
                cloudObject.setContentType(contentType);
                cloudObject.setOriginalName(fileName);
                cloudObject.setOriginal(Boolean.TRUE);
                cloudObject.setSize(file.getSize());
                cloudObject.setCreateDate(ZonedDateTime.now());

                User currentUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
                cloudObject.setCreatedBy(currentUser);

                cloudObject.setNetwork(libraryDB.getNetwork());
                cloudObject.setHash(ServiceConstants.NO_HASH);

                cloudObjectRepository.saveAndFlush(cloudObject);

                LIBMediaItem mediaItem = new LIBMediaItem();
                mediaItem.setItemType(LIBItemTypeEnum.IT_AUDIO);
                mediaItem.setName(ServiceConstants.NO_DATA);
                mediaItem.setIdx(mediaUtils.generateIdx(libraryDB));
                mediaItem.setLength(-1L);
                mediaItem.setState(LIBItemStateEnum.IS_NEW);
                mediaItem.setLibrary(libraryDB);

                mediaItemRepository.saveAndFlush(mediaItem);

                LIBAudioObject audioObject = new LIBAudioObject();
                audioObject.setBitrate(-1);
                audioObject.setCloudObject(cloudObject);
                audioObject.setMediaItem(mediaItem);
                audioObject.setQuality(LIBAudioQualityEnum.AQ_ORIGINAL);
                audioObject.setLength(-1L);
                audioObject.setCodec(ServiceConstants.NO_DATA);

                audioObjectRepository.saveAndFlush(audioObject);

                result.add(itemMapper.DB2DTO(mediaItem));
            } catch (UploadException e) {
                throw new MediaResourceException(e.getMessage());
            } catch (S3Exception e) {
                throw new MediaResourceException(e.getMessage());
            } finally {
                bais.close();
            }
        }
        return result;
    }

    public byte[] download(String networkShortcut, String libraryShortcut, String idx) throws DownloadException, IOException, MediaResourceException {

        byte[] result = null;

        LibItemPT item = getItem(networkShortcut, libraryShortcut, idx);
        if (item == null)
            return result;

        List<LIBAudioObject> audioObjects = audioObjectRepository.findByMediaItem(itemMapper.DTO2DB(item));

        if (audioObjects == null || audioObjects.size() == 0)
            return result;

        LIBAudioObject audioObject = audioObjects.iterator().next();

        LIBCloudObject cloudObject = audioObject.getCloudObject();

        InputStream stream = null;
        try {
            stream = s3Client.download(cloudObject.getUuid());

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-disposition", "filename=" + cloudObject.getOriginalName());
            responseHeaders.add("Content-Length", String.format("%d", cloudObject.getSize()));

            // TODO: usunac hardcode , brac dane z pola stream.contentType
            responseHeaders.add("Content-Type", "audio/mpeg");

            result = IOUtils.toByteArray(stream);
        } catch (DownloadException e) {
            throw new MediaResourceException(e.getMessage());
        } catch (S3Exception e) {
            throw new MediaResourceException(e.getMessage());
        } finally {
            stream.close();
        }
        return result;
    }
}
