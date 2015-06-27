# 聊天Demo
一个简单的聊天室程序，io.isharon.chat.server.ChatServer.main启动Server， io.isharon.chat.client.Client启动多个客户端。
客户端自动连服务端，开始聊天之前，客户端需要选取一个唯一用户名。

### 设计思路
采用Netty（NIO）框架，和基于文本的协议进行通讯。服务端通过循环扫描最近活跃时间来剔除长时间不活动的客户端。

### 改进点
* 代码结构需要改进
* 采用Filter模式替换Process模式
* 线程池改进，采用协程或许对性能有提升
* 后台管理
* 监控平台
* 部署友好
* 完善协议

### 水平扩展
由于Netty框架本身性能优异，单机可以支持10W用户同时在线。本系统引入ChatRoom的概念，各个ChatRoom之间可以认为无通信，可以将不同的ChatRoom，
部署到不同的Server上，从而做到水平扩展。