package com.wqj.implic

import java.io.File

import scala.io.Source

/**
  * @Auther: wqj
  * @Date: 2018/6/14 14:54
  * @Description:
  */

class RichFile(val f: File) {
  def read() = Source.fromFile(f).mkString
}

object RichFile {
  def main(args: Array[String]): Unit = {
    val f = new File("e://word.txt")
    //显示增强
    //    val f = new RichFile(f)
    //    println(f.read())

    //隐式增强
    import MyPredef.fileToRichFile
    println(f.read());




    //    val context=f.read()

  }

}
