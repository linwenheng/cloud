package com.project.XXcloud.Service;

import com.project.XXcloud.Mbg.Model.UserFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public interface UserFileService {
    public int uploadFile(UserFile userFile);
    public List<UserFile> selectFiles(int userId);
    public UserFile selectFile(int userId,String filename);
    public List<UserFile> selectFiles(int userId, String keyWord);
    public List<UserFile> selectMulTypeFIles(int userId, int... fileTypes);
    public int deleteFile(int userId,String fileName);
}
