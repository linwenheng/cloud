package com.project.XXcloud.Dto;

import org.springframework.web.multipart.MultipartFile;

public class FileInfo {
    public MultipartFile file;
    public int userId;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
