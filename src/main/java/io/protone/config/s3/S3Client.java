package io.protone.config.s3;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.errors.*;
import io.protone.config.ApplicationProperties;
import io.protone.config.s3.exceptions.DeleteException;
import io.protone.config.s3.exceptions.DownloadException;
import io.protone.config.s3.exceptions.S3Exception;
import io.protone.config.s3.exceptions.UploadException;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class S3Client {

    @Inject
    private ApplicationProperties applicationProperties;
    private MinioClient minioCLient;

    public MinioClient getClient() throws S3Exception {

        MinioClient client = null;

        try {
            if (client == null)
                client = new MinioClient(applicationProperties.getS3().getUrl(),
                    applicationProperties.getS3().getUsername(), applicationProperties.getS3().getPassword());

        } catch (InvalidEndpointException e) {
            throw new S3Exception(e.getMessage());
        } catch (InvalidPortException e) {
            throw new S3Exception(e.getMessage());
        }

        return client;
    }

    public void upload(String uuid, ByteArrayInputStream bais, String contentType) throws UploadException, S3Exception {

        try {
            getClient().putObject(applicationProperties.getS3().getBucket(), uuid, bais, bais.available(), contentType);
        } catch (InvalidBucketNameException e) {
            throw new UploadException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new UploadException(e.getMessage());
        } catch (InsufficientDataException e) {
            throw new UploadException(e.getMessage());
        } catch (IOException e) {
            throw new UploadException(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new UploadException(e.getMessage());
        } catch (NoResponseException e) {
            throw new UploadException(e.getMessage());
        } catch (XmlPullParserException e) {
            throw new UploadException(e.getMessage());
        } catch (ErrorResponseException e) {
            throw new UploadException(e.getMessage());
        } catch (InternalException e) {
            throw new UploadException(e.getMessage());
        } catch (InvalidArgumentException e) {
            throw new UploadException(e.getMessage());
        }
    }


    public InputStream download(String uuid) throws DownloadException, S3Exception {
        try {
            ObjectStat so = getClient().statObject(applicationProperties.getS3().getBucket(), uuid);
            return getClient().getObject(applicationProperties.getS3().getBucket(), uuid);
        } catch (InvalidBucketNameException e) {
            throw new DownloadException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new DownloadException(e.getMessage());
        } catch (InsufficientDataException e) {
            throw new DownloadException(e.getMessage());
        } catch (IOException e) {
            throw new DownloadException(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new DownloadException(e.getMessage());
        } catch (NoResponseException e) {
            throw new DownloadException(e.getMessage());
        } catch (XmlPullParserException e) {
            throw new DownloadException(e.getMessage());
        } catch (ErrorResponseException e) {
            throw new DownloadException(e.getMessage());
        } catch (InternalException e) {
            throw new DownloadException(e.getMessage());
        } catch (InvalidArgumentException e) {
            throw new DownloadException(e.getMessage());
        }
    }

    public void delete(String uuid) throws DeleteException, S3Exception {
        try {
            ObjectStat so = getClient().statObject(applicationProperties.getS3().getBucket(), uuid);
            getClient().removeObject(applicationProperties.getS3().getBucket(), uuid);
        } catch (InvalidBucketNameException e) {
            throw new DeleteException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new DeleteException(e.getMessage());
        } catch (InsufficientDataException e) {
            throw new DeleteException(e.getMessage());
        } catch (IOException e) {
            throw new DeleteException(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new DeleteException(e.getMessage());
        } catch (NoResponseException e) {
            throw new DeleteException(e.getMessage());
        } catch (XmlPullParserException e) {
            throw new DeleteException(e.getMessage());
        } catch (ErrorResponseException e) {
            throw new DeleteException(e.getMessage());
        } catch (InternalException e) {
            throw new DeleteException(e.getMessage());
        }
    }
}
