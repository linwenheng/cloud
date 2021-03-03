package com.project.XXcloud.Service;

import com.project.XXcloud.Mbg.Model.UserFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public interface UserFileService {
    public int uploadFile(UserFile userFile);
    public List<UserFile> seleteFile(int userId);
    public List<UserFile> seleteMulTypeFIle(int userId,int... fileTypes);
    public int deleteFile(int userId,String fileName);
}
