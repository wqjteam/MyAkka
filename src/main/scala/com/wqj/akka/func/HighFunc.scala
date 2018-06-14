package com.wqj.akka.func

/**
  * @Auther: wqj
  * @Date: 2018/6/13 22:59
  * @Description:
  */
object HighFunc extends App {

  val fun = (x: Int) => {

    x * x
  }
  //    def a: Int => Int = { x => x * x }
  val fun2: Int => Int = { x => x * x }

  //  print(fun)
  //  print(fun2)

  def mix(x: Int): Int = {
    x * x
  }

  val arr = Array(1, 2, 3, 4, 5, 6)
  //val arr2=arr.map(fun)
  //val arr2=arr.map(fun2)
  //val arr2=arr.map(mix _)
  val arr2 = arr.map(mix)
  println(arr2.toBuffer)


  //柯理化1
  def func4(x: Int)(y: Int): Int = {
    x * y
  }

  //柯理化1
  def func5(x: Int) = (y: Int) => {
    x * y


  }

  def func4x = func4(2)(_)

  print(func4x(4))
}
