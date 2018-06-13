package com.wqj.akka.rpcplus

/**
  * @Auther: wqj
  * @Date: 2018/6/13 17:56
  * @Description:
  */
trait RemoteMessage extends Serializable

case class RegisterWoeker(id: String, ip: String, port: String) extends RemoteMessage

case class Heartbeat(id: String) extends RemoteMessage


//Master -> Worker
case class RegisteredWorker(masterUrl: String) extends RemoteMessage

//Worker -> self
case object SendHeartbeat

// Master -> self
case object CheckTimeOutWorker