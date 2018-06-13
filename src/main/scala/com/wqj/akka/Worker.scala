package com.wqj.akka

import akka.actor.Actor

/**
  * @Auther: wqj
  * @Date: 2018/6/13 11:25
  * @Description:  所有的Actor不经要发送消息  还要接收消息
  */
class Worker extends Actor{

  println("执行Worker构造器")


  override def preStart(): Unit = {
  println("执行Worker初始化init方法")

  }

  override def receive = {

    case  "reply" =>{
      println("接收到 一个reply")
    }
  }
}
