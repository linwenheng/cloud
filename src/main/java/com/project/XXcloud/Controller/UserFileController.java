package com.project.XXcloud.Controller;

import com.project.XXcloud.Dto.FileInfo;
import com.project.XXcloud.HDFS.HDFSOperation;
import com.project.XXcloud.Mbg.Model.UserFile;
import com.project.XXcloud.Service.UserFileService;
import com.project.XXcloud.Service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

@Controller
public class UserFileController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserFileService userFileService;


    /*
    *查询文件列表(用户所有文件）
    * userID 用户ID
     */
    @PostMapping("/file/list")
    @ResponseBody
    public List<UserFile> getFileList(int userID)
    {
        return userFileService.selectFiles(userID);
    }

    /*
     *查询文件列表(指定类型文件）
     * userID 文件所属用户ID，fileTypes 文件类型
     */
    @PostMapping("/file/mulTypeFile")
    @ResponseBody
    public List<UserFile> getMulTypeList(int userID,int... fileTypes)
    {
        return userFileService.selectMulTypeFIles(userID,fileTypes);
    }

    /*
     *查询文件列表(指定关键词）
     * userID 文件所属用户ID，keyword 关键词
     */
    @PostMapping("/file/selectByKeyword")
    @ResponseBody
    public List<UserFile> selectByKeyword(int userID,String keyword)
    {
        return userFileService.selectFiles(userID,keyword);
    }
    /*
    *删除文件
     */
    @PostMapping("/file/delete")
    @ResponseBody
    public int deleteFile(int userId,String filename) throws IOException
    {

        String email = userInfoService.selectUserInfoByID(userId).getEmail();
        HDFSOperation.deleteFile(email,filename);
        return userFileService.deleteFile(userId,filename);
    }
    /*
     *文件上传
     * 第一个参数为上传的文件，第二个参数为用户邮箱
     * 上传成功返回1，空文件返回0，上传失败返回-1
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);
    @PostMapping("/file/upload")
    @ResponseBody
    public int upload(FileInfo fileInfo) {
        MultipartFile file = fileInfo.getFile();
        int userId = fileInfo.getUserId();
        LOGGER.info("UserID" + String.valueOf(userId));
        String email = userInfoService.selectUserInfoByID(userId).getEmail();
        if (file.isEmpty()) {
            return 0;
        }

        String fileName = file.getOriginalFilename();

        try {

            HDFSOperation.uploadFile(email,fileName,file.getBytes());
            if(userFileService.selectFile(userId,fileName) != null) userFileService.deleteFile(userId,fileName);
            UserFile userFile = new UserFile();
            userFile.setCreateDate(new Date());
            userFile.setUserId(userId);
            userFile.setFileName(fileName);
            int fileType = 0;
            if(fileName.endsWith(".txt")) fileType = 0;
            if(fileName.endsWith(".doc")) fileType = 1;
            if(fileName.endsWith(".jpg")) fileType = 2;
            if(fileName.endsWith(".png")) fileType = 3;
            userFile.setFileType(fileType);
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
    @PostMapping("/file/download")
    @ResponseBody
    public void download(String filename, int userId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = userInfoService.selectUserInfoByID(userId).getEmail();
        String mimeType=request.getServletContext().getMimeType(filename);
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition","attachement;filename="+filename);
        try
        {
            OutputStream os=response.getOutputStream();
            HDFSOperation.downloadFile(email,filename,os);
            os.flush();
//            IOUtils.copy(in,response.getOutputStream());

            LOGGER.info("下载成功");
        }
        catch (IOException e)
        {
            LOGGER.error(e.toString(), e);
        }

    }
}
