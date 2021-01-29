package com.project.XXcloud.ServiceImpl;

import com.project.XXcloud.Mbg.Mapper.UserFileMapper;
import com.project.XXcloud.Mbg.Model.UserFile;
import com.project.XXcloud.Service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
@Service
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    private UserFileMapper userFileMapper;
    @Override
    public int uploadFile(UserFile userFile) {
        return userFileMapper.insert(userFile);

    }
}
