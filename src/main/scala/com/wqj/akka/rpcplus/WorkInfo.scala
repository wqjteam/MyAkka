package com.wqj.akka.rpcplus

/**
  * @Auther: wqj
  * @Date: 2018/6/13 21:05
  * @Description:
  */
class WorkInfo(val id:String,ip:String,port:String)  {

  //TODO 上一次心跳
  var lastHeartbeatTime : Long = _
}
