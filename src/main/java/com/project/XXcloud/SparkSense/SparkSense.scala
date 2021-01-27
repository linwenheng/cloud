package com.project.XXcloud.SparkSense

import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source
import java.io.File

import XMLUtil.XMLUtil
import com.project.XXcloud.HDFS.HDFSOperation

object SparkSense {
  private val hdfsConf = XMLUtil.getHdfsConf();
  private val ip = hdfsConf(0);
  private val port = hdfsConf(1);
  private var conf: SparkConf = null;
  private var sc: SparkContext = null;
  //  private var ssc:StreamingContext=null;
  private val inputFile = Source.fromFile("C:\\Users\\Amaze\\Desktop\\testFiles\\sensiveWords.txt");
  private var sensiveWords = Array[String]();


  /**
   *
   * */
  private def initialSpark(appName: String): Unit = {
    conf = new SparkConf().setAppName(appName).setMaster("local[*]");
    sc = new SparkContext(conf);
    //    ssc = new StreamingContext(conf, Seconds(5))
  }

  private def getSensiveWords(): Unit = {
    for (line <- inputFile.getLines()) {
      sensiveWords = sensiveWords ++ line.split("\\s+")
    }
  }

  //优化方案，先用contains方法检测每一行的字符串里有没有敏感词，若有敏感词就把该字符串传到处理函数，返回处理后的字符串，若没有敏感词则返回原字符串

  /**
   * 功能：分析单个文本文件，屏蔽敏感词
   * 传入参数：参数1：用户文件夹名称（即用户邮箱），参数2：文件名
   * 返回值：无
   * */
  def analyzeTextFile(email: String, fileName: String): Unit = {
    initialSpark(email + "_" + fileName);
    getSensiveWords();

    val data = sc.textFile("hdfs://" + ip + ":" + port + "/" + email + "/" + fileName);
    data.map(x => analyzeLines(x)).repartition(1).saveAsTextFile("C:\\Users\\Amaze\\Desktop\\testFiles\\" + email + "tmp");

    HDFSOperation.writeFile(email, fileName, "C:\\Users\\Amaze\\Desktop\\testFiles\\" + email + "tmp\\part-00000");
    val path: File = new File("C:\\Users\\Amaze\\Desktop\\testFiles\\" + email + "tmp");
    deleteDir(path);

  }

  /**
   * 功能：分析字符串，若有敏感词则将其屏蔽
   * 参数1：要分析的字符串
   * 返回值：处理后的字符串
   * */

  private def analyzeLines(line: String): String = {
    var flag: Int = 0; //是否有敏感词的标记
    var sen_words: Array[String] = Array[String](); //记录所包含的敏感词
    for (swNum <- 0 to sensiveWords.length - 1) {
      if (line.contains(sensiveWords(swNum))) //依次检测该行里是否含有某个敏感词
      {
        flag = 1; //若有敏感词，标记为true
        sen_words = sen_words :+ sensiveWords(swNum); //记录所包含的敏感词
      }
    }
    if (flag == 0) //如果没有敏感词，返回原字符串
    {
      line;
    }
    else {
      processLines(line, sen_words); //如果有敏感词，返回处理后的字符串
    }

  }

  /**
   * 功能：处理字符串，将字符串中的敏感词屏蔽
   * 参数1：要处理的字符串； 参数2：要处理的字符串中包含的敏感词数组
   * 返回值：处理后的字符串
   * */
  private def processLines(line: String, sen_words: Array[String]): String = {
    val wordsList: Array[String] = line.split("\\s+"); //把需要处理的字符串分割成字符数组
    var res: String = ""; //最终的返回结果
    for (num <- 0 to wordsList.length - 1) //遍历整个字符数组
    {
      for (swNum <- 0 to sen_words.length - 1) //遍历整个敏感词数组
        if (wordsList(num).contains(sen_words(swNum))) {
          wordsList(num) = "**";
        }
      res += wordsList(num) + " ";
    }
    res.trim();
  }

  /**
   * 功能：传入一个字符串，返回分割后的字符数组
   * 参数1：要分割的字符串
   * 返回值：分割后的字符数组
   * */
  private def splitLines(line:String): Array[String] =
  {
    val wordsList:Array[String]=Array[String]();

    wordsList;
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
