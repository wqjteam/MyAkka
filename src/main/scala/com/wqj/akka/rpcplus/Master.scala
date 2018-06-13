package com.wqj.akka.rpcplus

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.concurrent.duration._

/**
  * @Auther: wqj
  * @Date: 2018/6/12 23:38
  * @Description:
  */
class Master(val masterHost: String, val masterPort: Int) extends Actor {

  println("执行Master构造器")

  //这是worker的心跳数据
  val workerMap = new mutable.HashMap[String, WorkInfo]()

  val workers = new mutable.HashSet[WorkInfo]()

  override def preStart(): Unit = {
    //初始化 可以在这里写业务逻辑
    println("Master_preStart init")
    //给自己发送数据,定时清除死忙的worker
    //导入隐式转换
    import context.dispatcher //使用timer太low了, 可以使用akka的, 使用定时器, 要导入这个包
    context.system.scheduler.schedule(0 millis, 15000 millis, self, CheckTimeOutWorker)
  }

  //用户接收消息
  override def receive: Receive = {

    case Heartbeat(id) => {
      if (workerMap.contains(id)) {
        val workerInfo = workerMap(id)
        //报活
        val currentTime = System.currentTimeMillis()
        workerInfo.lastHeartbeatTime = currentTime
      }
    }

    //删除死亡的worker
    case CheckTimeOutWorker => {
      val currentTime = System.currentTimeMillis()
      val toRemove = workers.filter(x => currentTime - x.lastHeartbeatTime > 5000)
      for (w <- toRemove) {
        workers -= w
        workerMap -= w.id
      }
      println(workers.size)
    }
  }
}

object Master {

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
    val actorSystem = ActorSystem("MasterSystem", config)
    //通过ActorSystem创建TeacherActor的代理(ActorRef)  就是子类的actor
    //    ActorSystem通过actorOf创建Actor，但其并不返回TeacherActor而是返
    //    回一个类型为ActorRef的东西。
    //    ActorRef作为Actor的代理，使得客户端并不直接与Actor对话，这种Actor
    //    模型也是为了避免TeacherActor的自定义/私有方法或变量被直接访问，所
    //    以你最好将消息发送给ActorRef，由它去传递给目标Actor
    val master = actorSystem.actorOf(Props(new Master(host, port)), "Master")
    //进程等待结束,不关闭,优雅推出
    //    自己给自己发送消息
    //    master ! "haha"
    actorSystem.awaitTermination()
  }
}
