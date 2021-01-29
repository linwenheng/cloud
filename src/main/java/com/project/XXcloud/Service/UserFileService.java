package com.project.XXcloud.Service;

import com.project.XXcloud.Mbg.Model.UserFile;
import org.springframework.beans.factory.annotation.Autowired;



public interface UserFileService {
    public int uploadFile(UserFile userFile);
}
