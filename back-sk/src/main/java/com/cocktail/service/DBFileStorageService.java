package com.cocktail.service;

import org.springframework.web.multipart.MultipartFile;
import com.cocktail.model.DBFile;

public interface DBFileStorageService {
    public DBFile storeFile(MultipartFile file);

    public DBFile getFile(String fileId);
}