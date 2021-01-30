package com.project.XXcloud.SplitWords

import scala.io.Source

object SensiveWords {
  private val inputFile = Source.fromFile("C:\\Users\\Amaze\\Desktop\\testFiles\\sensiveWords.txt");
  var sensiveWords = Array[String]();

  for (line <- inputFile.getLines()) {
    sensiveWords = sensiveWords ++ line.split("\\s+")
  }
}
