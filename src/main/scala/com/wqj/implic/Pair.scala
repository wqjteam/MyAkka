package com.wqj.implic

/**
  * @Auther: wqj
  * @Date: 2018/6/14 15:42
  * @Description:  学习限制类型
  */
class Pair[T <: Comparable[T]] {

  // T 必须实现Comparable接口,是Comparable的子类
  def bigger(first:T,second: T)={
    if (first > second) first else second
  }
}
