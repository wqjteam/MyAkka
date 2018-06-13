package com.wqj.akka.baserpc

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import com.wqj.akka.rpcplus.{Heartbeat, RegisterWoeker, RegisteredWorker, SendHeartbeat}
import scala.concurrent.duration._

/**
  * @Auther: wqj
  * @Date: 2018/6/13 11:25
  * @Description: 所有的Actor不经要发送消息  还要接收消息
  */
class Worker(val masterHost: String, val masterPort: Int) extends Actor {

  println("执行Worker构造器")
  var master: ActorSelection = _
  val workerId = UUID.randomUUID().toString

  //  建立连接
  override def preStart(): Unit = {
    println("执行Worker初始化init方法")

    //    work要向master发送消息 ,必须先拿到master代理对象,就是引用,
    //    1.从actorsystem中获取相关信息
    //    2.想master发送消息

    //获取连接tcp
    //1.MasterSystem是新建MasterSystem就确定命名的,2必须包含/user这是规定,3.Master这是在Master中actorOf中命名已经规定了
    master = context.actorSelection(s"akka.tcp://MasterSystem@$masterHost:$masterPort/user/Master")
    //向master发送消息
    master ! RegisterWoeker(workerId, "127.0.0.1", "8888")
  }

  override def receive = {

    case RegisteredWorker(masterUrl) => {
      println(masterUrl)
      //启动定时器发送心跳
      import context.dispatcher
      //多长时间后执行 单位,多长时间执行一次 单位, 消息的接受者(直接给master发不好, 先给自己发送消息, 以后可以做下判断, 什么情况下再发送消息), 信息
      context.system.scheduler.schedule(0 millis, 10000 millis, self, SendHeartbeat)
    }

    //先检查自己有没有死亡,正常的话 就想master发行消息
    case SendHeartbeat => {
      println("send heartbeat to master")
      master ! Heartbeat(workerId)
    }
  }
}

object Worker {
  def main(args: Array[String]): Unit = {
    //actorSystem 负责监控和创建下面的actor,并且他是单列的
    val host = args(0)
    val port = args(1).toInt
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    //    ActorSystem作为顶级Actor，可以创建和停止Actors,甚至可关闭整个Actor环境，
    //    此外Actors是按层次划分的，ActorSystem就好比Java中的Object对象，Scala中的Any，
    //    是所有Actors的根，当你通过ActorSystem的actof方法创建Actor时，实际就是在ActorSystem
    //    下创建了一个子Actor。

    //初始化系统级别actor
    val actorSystem = ActorSystem("WorkerSystem", config)
    //通过ActorSystem创建TeacherActor的代理(ActorRef)  就是子类的actor
    //    ActorSystem通过actorOf创建Actor，但其并不返回TeacherActor而是返
    //    回一个类型为ActorRef的东西。
    //    ActorRef作为Actor的代理，使得客户端并不直接与Actor对话，这种Actor
    //    模型也是为了避免TeacherActor的自定义/私有方法或变量被直接访问，所
    //    以你最好将消息发送给ActorRef，由它去传递给目标Actor
    val master = actorSystem.actorOf(Props(new Worker(host, 6666)), "Worker")
    //进程等待结束,不关闭,优雅推出
    //    自己给自己发送消息
    //    master ! "haha"
    actorSystem.awaitTermination()
  }


}
