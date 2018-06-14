package com.wqj.implic

/**
  * @Auther: wqj
  * @Date: 2018/6/14 11:23
  * @Description:
  */
object implicitValue {
  def sayHi()(implicit name:String="张三"):Unit={
    print("hi:"+name)
  }

  def main(args: Array[String]): Unit = {
    sayHi()
  }
}
