import akka.actor.{Actor, ActorSystem}
import akka.remote.transport.ThrottlerTransportAdapter.Direction.Receive
import com.typesafe.config.ConfigFactory

/**
  * @Auther: wqj
  * @Date: 2018/6/12 23:38
  * @Description:
  */
class Master extends Actor{


  override def preStart(): Unit ={
    super.preStart()
  }

  //用户接收消息
  override def receive: Receive = {

    case "connect" => print("a clinet connect")

  }
}

object Master{

  def main(args: Array[String]): Unit = {
    //actorSystem 负责监控和创建下面的actor,并且他是单列的
    val host =args(0)
    val port = args(1).toInt
    val configStr=s""
    val config = ConfigFactory.parseString(configStr)
    val actorSystem =ActorSystem("MasterSystem",config )
  }
}
