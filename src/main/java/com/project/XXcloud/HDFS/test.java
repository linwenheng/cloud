package com.project.XXcloud.HDFS;

import org.apache.hadoop.security.SaslOutputStream;

import java.io.*;
import java.util.ArrayList;

public class test
{
    public static void main(String[] args) throws Exception
    {
//        File file=new File("C:\\Users\\Amaze\\Desktop\\testFiles\\dd.txt");
//        HDFSOperation.uploadFile("qiushuoqi@qqq.com","test.txt",file);


        HDFSOperation.deleteFile("qiushuoqi@qq.com","dd.txt");


        //创建目录
//        HDFSOperation.createDir("test@qq.com");
//

        //上传文件
//        InputStream in=new BufferedInputStream(new FileInputStream("D:\\学校\\实训\\阶段性汇报ppt\\原型.rar"));
//        HDFSOperation.uploadFile("test@qq.com","原型.rar",in);


        //获取目录下所有文件名
//            ArrayList<String> files= HDFSOperation.listFile("qiushuoqi@qq.com");
//            for(String f:files)
//            {
//                System.out.println(f);
//            }


        //下载文件

//        File file=HDFSOperation.downloadFile("qiushuoqi@qq.com","aa.txt");
//        System.out.println(file.isFile());
//        System.out.println(file.getAbsolutePath());
//        File file=new File("hdfs://127.0.0.1:9000/qiushuoqi@qq.com/aa.txt");
//        InputStream in=new BufferedInputStream(new FileInputStream(file));



        //删除文件
//        HDFSOperation.deleteFile("test@qq.com","原型.rar");

        //删除目录
//        HDFSOperation.deleteFile("test@qq.com");

    }
}
