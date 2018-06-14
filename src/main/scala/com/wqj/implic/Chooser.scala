package com.wqj.implic

/**
  * @Auther: wqj
  * @Date: 2018/6/14 16:11
  * @Description:
  */

//视图界定  view bound 必须穿进去一个隐式转换的函数
//class Chooser[T <% Ordered[T]] {
//
//  def choose(first: T, second: T): T = {
//
//    if (first > second)
//      first
//    else
//      second
//
//  }
//}

//上下文界定  他必须传进去一个隐式转换的值/函数
class Chooser[T : Ordering] {

  def choose(first: T, second: T): T = {
    val ord =implicitly[Ordering[T]]
//    if (first > second)
//      first
//    else
//      second
    second
  }
}
object Chooser {
  def main(args: Array[String]): Unit = {

//    因为这是在类中,必须在类创建之前,就传转换函数,  可转换比较方式 非常灵活
    import MyPredef.girltoordered
    val c = new Chooser[Girl]
    val g1 = new Girl("aa", 90)
    val g2 = new Girl("bb", 99)
    print(c.choose(g1, g2).name)
  }

}
