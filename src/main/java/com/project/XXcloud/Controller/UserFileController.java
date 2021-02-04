package com.project.XXcloud.Controller;

import com.project.XXcloud.HDFS.HDFSOperation;
import com.project.XXcloud.Mbg.Mapper.UserInfoMapper;
import com.project.XXcloud.Mbg.Model.UserFile;
import com.project.XXcloud.Service.UserFileService;
import com.project.XXcloud.Service.UserInfoService;
import io.netty.util.internal.UnstableApi;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @GetMapping("/download")
    public String download() {
        return "download";
    }

    @GetMapping("/delete")
    public String delete() {
        return "delete";
    }
    /*
    *查询文件列表
     */
    @PostMapping("/file/list")
    @ResponseBody
    public List<UserFile> getFileList(int userId)
    {
        return userFileService.seleteFile(userId);
    }

    /*
    *删除文件
     */
    @PostMapping("/file/delete")
    @ResponseBody
    public int deleteFile(int userId,String email,String filename) throws IOException
    {
        HDFSOperation.deleteFile(email,filename);
        return userFileService.deleteFile(userId,filename);
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
        try {

            HDFSOperation.uploadFile(email,fileName,file.getBytes());

            UserFile userFile = new UserFile();
            userFile.setCreateDate(new Date());
            userFile.setUserId(userInfoService.selectUserInfoByEmail(email).getUserId());
            userFile.setFileName(fileName);
            userFileService.uploadFile(userFile);
            LOGGER.info("上传成功");
            return 1;
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    /*
    *下载文件
    * 参数1为文件名，参数2为用户邮箱
     */
    @PostMapping("/download")
    @ResponseBody
    public void download(String filename, String email, HttpServletRequest request, HttpServletResponse response) throws IOException {

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
