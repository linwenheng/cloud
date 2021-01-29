package com.project.XXcloud.Controller;

import com.project.XXcloud.HDFS.HDFSOperation;
import com.project.XXcloud.Mbg.Mapper.UserInfoMapper;
import com.project.XXcloud.Mbg.Model.UserFile;
import com.project.XXcloud.Service.UserFileService;
import com.project.XXcloud.Service.UserInfoService;
import io.netty.util.internal.UnstableApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
public class UserFileController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserFileService userFileService;

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }
    /*
     *文件上传
     * 第一个参数为上传的文件，第二个参数为用户邮箱
     * 上传成功返回1，空文件返回0，上传失败返回-1
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);
    @PostMapping("/upload")
    @ResponseBody
    public int upload(@RequestParam("file") MultipartFile file, String email) {
        if (file.isEmpty()) {
            return 0;
        }

        String fileName = file.getOriginalFilename();
        File dest = new File(fileName);
        try {
            file.transferTo(dest);

            HDFSOperation.uploadFile(email,fileName,dest);

            UserFile userFile = new UserFile();
            userFile.setCreateDate(new Date());
            userFile.setUserId(userInfoService.selectUserInfoByEmail(email).getUserId());
            userFile.setFileName(fileName);
            userFileService.uploadFile(userFile);
            LOGGER.info("上传成功");
            return 1;
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return -1;
    }
    /*
    *下载文件
    * 参数1为文件名，参数2为用户邮箱
     */
    @PostMapping("/download")
    @ResponseBody
    public File download(String filename,String email)
    {
        File dest = new File(filename);
        try
        {

            HDFSOperation.downloadFile(email,filename,dest);

            LOGGER.info("下载成功");
        }
        catch (IOException e)
        {
            LOGGER.error(e.toString(), e);
        }
        return dest;
    }
}
