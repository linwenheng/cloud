package com.project.XXcloud.SparkSense

import java.io.File

import XMLUtil.XMLUtil
import com.project.XXcloud.HDFS.HDFSOperation
import com.project.XXcloud.SplitWords.SplitWords
import org.apache.spark.{SparkConf, SparkContext}


object SparkSense {
  private val hdfsConf = XMLUtil.getHdfsConf();
  private val ip = hdfsConf(0);
  private val port = hdfsConf(1);
  private var conf: SparkConf = null;
  private var sc: SparkContext = null;


  /**
   *
   * */
  private def initialSpark(appName: String): Unit = {
    conf = new SparkConf().setAppName(appName).setMaster("local[*]");
    sc = new SparkContext(conf);

  }



  //优化方案，先用contains方法检测每一行的字符串里有没有敏感词，若有敏感词就把该字符串传到处理函数，返回处理后的字符串，若没有敏感词则返回原字符串

  /**
   * 功能：分析单个文本文件，屏蔽敏感词
   * 传入参数：参数1：用户文件夹名称（即用户邮箱），参数2：文件名
   * 返回值：无
   * */
  def analyzeTextFile(email: String, fileName: String): Unit = {
    initialSpark(email + "_" + fileName);

    val data = sc.textFile("hdfs://" + ip + ":" + port + "/" + email + "/" + fileName);
    data.map(x =>SplitWords.splitWords(x)).repartition(1).saveAsTextFile("C:\\Users\\Amaze\\Desktop\\testFiles\\" + email + "tmp");

    HDFSOperation.writeFile(email, fileName, "C:\\Users\\Amaze\\Desktop\\testFiles\\" + email + "tmp\\part-00000");
    val path: File = new File("C:\\Users\\Amaze\\Desktop\\testFiles\\" + email + "tmp");
    deleteDir(path);

  }


  /**
   * 删除一个文件夹,及其子目录
   *
   * @param dir
   */
  private def deleteDir(dir: File): Unit = {
    val files = dir.listFiles()
    files.foreach(f => {
      if (f.isDirectory) {
        deleteDir(f)
      } else {
        f.delete()
      }
    })
    dir.delete()

  }
}
