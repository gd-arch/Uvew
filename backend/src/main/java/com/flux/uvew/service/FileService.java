package com.flux.uvew.service;

import org.springframework.web.multipart.MultipartFile;
import com.flux.uvew.exceptions.*;

import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile multipartFile) throws FileUploadException, IOException;

    Object downloadFile(String fileName) throws FileDownloadException, IOException;

    boolean delete(String fileName);
}
