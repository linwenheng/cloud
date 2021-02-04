package com.project.XXcloud.FileProcess;

import XMLUtil.XMLUtil;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;

public class ImageFileProcess implements FileProcess
{

    @Override
    public void fileAnalyze(String email, String fileName, FileSystem fileSystem, String url)
    {
        try
        {
            String tmpPath= XMLUtil.getTemDir()+"\\"+fileName;
            OutputStream fos=new FileOutputStream(tmpPath);
            InputStream is=fileSystem.open(new Path(url));
            IOUtils.copyBytes(is,fos,4096,false);
            is.close();
            fos.close();
            if(ImageCheck.imageCheck(tmpPath))
            {
                System.out.println("敏感图片！");
                Path delef;
                delef=new Path(url);
                System.out.println(fileSystem.delete(delef,true));

                is=new FileInputStream(new File("C:\\Users\\Amaze\\Desktop\\testFiles\\test3.png"));
                fos=fileSystem.create(new Path(url));
                IOUtils.copyBytes(is,fos,4096,true);
                is.close();
                fos.flush();
                fos.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
