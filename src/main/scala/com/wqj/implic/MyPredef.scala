package com.wqj.implic

import java.io.File

/**
  * @Auther: wqj
  * @Date: 2018/6/14 15:00
  * @Description: 隐式转换 从java的io转为scala
  */
object MyPredef {

  implicit def fileToRichFile(f: File) = {
    new RichFile(f)
  }

  //第二个隐式转换
  implicit  def girltoordered(g:Girl) =new Ordered[Girl]{
    override def compare(that: Girl) ={
      g.faceValue-that.faceValue
    }
  }

  implicit  val girltoordering =new Ordering[Girl]{
    override def compare(x: Girl, y: Girl) = {
      x.faceValue-y.faceValue
    }
  }
}
