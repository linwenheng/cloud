package com.project.XXcloud.FileProcess;


import com.project.XXcloud.SparkSense.SparkSense;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DocFileProcess implements FileProcess
{

    @Override
    public void fileAnalyze(String email, String fileName,FileSystem fileSystem,String url)
    {
        InputStream fis=null;
        Range range=null;
        OutputStream fos=null;
        try
        {
            Path path=new Path(url);
            fis=fileSystem.open(path);
            HWPFDocument hwpfDocument=new HWPFDocument(fis);
            range=hwpfDocument.getRange();

            List<String> originText=new ArrayList<String>();

            int num=range.numParagraphs();
            for(int i=0;i<num;++i)
            {
                originText.add(range.getParagraph(i).text());
            }
            Seq<String> tmpSeq = JavaConverters.asScalaIteratorConverter(originText.iterator()).asScala().toSeq();
            List<String> resultText= SparkSense.analyzeWordText(tmpSeq,num);
            for(int i=0;i<num;++i)
            {
                Paragraph p=range.getParagraph(i);
                String content=p.text();
                if(!(resultText.get(i).equals(content)))
                    p.replaceText(content,resultText.get(i));
            }


           fos=fileSystem.create(path);
            hwpfDocument.write(fos);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            range.delete();
            try
            {
                fos.close();
                fis.close();
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }


    }
}
