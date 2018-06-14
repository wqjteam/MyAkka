package com.wqj.implic

import java.io.File

import Context._

/**
  * @Auther: wqj
  * @Date: 2018/6/14 11:23
  * @Description:
  */
//所有的隐式值都必须放到object中
object Context {
  implicit val a = "李四"
  //  implicit  val b ="赵五"
  implicit val i = 1
}


object implicitValue {
  def sayHi()(implicit name: String = "张三"): Unit = {
    print(s"hi:$name")
  }

  def main(args: Array[String]): Unit = {
    //会找类型一样的  去替换隐式参数的数据,两个相同的就会报错

    sayHi()
  }

}


