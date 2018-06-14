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
    val f = new File("")
    //显示的增强
    //    val contents = new RichFile(f).read()


    //隐式增强
    import MyPredef.fileToRichFile
    f.read();




    //    val context=f.read()

  }

}
