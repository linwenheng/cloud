package com.project.XXcloud.ServiceImpl;

import com.project.XXcloud.Mbg.Mapper.UserFileMapper;
import com.project.XXcloud.Mbg.Model.UserFile;
import com.project.XXcloud.Mbg.Model.UserFileExample;
import com.project.XXcloud.Service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    private UserFileMapper userFileMapper;
    @Override
    public int uploadFile(UserFile userFile) {
        return userFileMapper.insert(userFile);

    }

    @Override
    public List<UserFile> seleteFile(int userId) {
        UserFileExample userFileExample = new UserFileExample();
        userFileExample.or().andUserIdEqualTo(userId);
        return userFileMapper.selectByExample(userFileExample);
    }

    @Override
    public int deleteFile(int userId, String fileName) {
        UserFileExample userFileExample = new UserFileExample();
        userFileExample.or().andUserIdEqualTo(userId).andFileNameEqualTo(fileName);

        return userFileMapper.deleteByExample(userFileExample);
    }
}
