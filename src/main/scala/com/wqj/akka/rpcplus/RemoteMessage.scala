package com.wqj.akka.rpcplus

/**
  * @Auther: wqj
  * @Date: 2018/6/13 17:56
  * @Description:
  */
trait RemoteMessage extends Serializable{
case class RegisterWoeker(id:String)
}
