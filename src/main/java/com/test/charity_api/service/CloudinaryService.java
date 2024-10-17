package com.test.charity_api.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    public String uploadFile(MultipartFile file);
}
