package com.project.XXcloud.ServiceImpl;

import com.project.XXcloud.Mbg.Mapper.UserFileMapper;
import com.project.XXcloud.Mbg.Model.UserFile;
import com.project.XXcloud.Mbg.Model.UserFileExample;
import com.project.XXcloud.Service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
    public List<UserFile> selectFiles(int userId) {
        UserFileExample userFileExample = new UserFileExample();
        userFileExample.or().andUserIdEqualTo(userId);
        return userFileMapper.selectByExample(userFileExample);
    }

    @Override
    public UserFile selectFile(int userId,String filename) {
        UserFileExample userFileExample = new UserFileExample();
        userFileExample.or().andUserIdEqualTo(userId).andFileNameEqualTo(filename);
        List<UserFile> list = userFileMapper.selectByExample(userFileExample);
        if(list.size() == 1) return list.get(0);
        return null;
    }

    @Override
    public List<UserFile> selectFiles(int userId, String keyWord) {
        UserFileExample userFileExample = new UserFileExample();
        userFileExample.or().andUserIdEqualTo(userId).andFileNameLike("%" + keyWord+"%");
        List<UserFile> list = userFileMapper.selectByExample(userFileExample);
        return list;
    }

    @Override
    public List<UserFile> selectMulTypeFIles(int userId, int... fileTypes) {
        UserFileExample userFileExample = new UserFileExample();
        List<Integer> list = new ArrayList<>();
        for(int i:fileTypes)
        {
            list.add(i);
        }
        userFileExample.or().andUserIdEqualTo(userId).andFileTypeIn(list);

        return userFileMapper.selectByExample(userFileExample);
    }

    @Override
    public int deleteFile(int userId, String fileName) {
        UserFileExample userFileExample = new UserFileExample();
        userFileExample.or().andUserIdEqualTo(userId).andFileNameEqualTo(fileName);

        return userFileMapper.deleteByExample(userFileExample);
    }
}
