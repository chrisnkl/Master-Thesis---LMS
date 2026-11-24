package com.chrisnkl.lmscourseservice.application.port.out;

import java.time.Duration;

public interface FileStoragePort {

    String generateUploadUrl(String blobKey, String contentType, Duration ttl);
    String generateDownloadUrl(String blobKey, String originalFileName, Duration ttl);
    boolean exists(String blobKey);
    void delete(String blobKey);

}
