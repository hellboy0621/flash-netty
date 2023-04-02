## 掘金小册 《Netty 入门与实战：仿写微信 IM 即时通讯系统》
https://juejin.im/book/5b4bc28bf265da0f60130116

## 京东实体书 《跟闪电侠学 Netty》
https://item.jd.com/13611960.html


## 友情提醒
master 为项目全量代码，建议读者根据章节顺序，切换到每个章节对应的代码分支，循序渐进学习。

## 更多精彩
更多源码阅读技巧相关的文章，欢迎关注 《闪电侠的博客》

![2](https://user-images.githubusercontent.com/1680506/155874099-4a45c891-633f-43b9-bea7-6c5ae9c6c4e1.png)


<img width="465" alt="image" src="https://user-images.githubusercontent.com/1680506/155873995-cee9ace0-7ef8-4296-b586-46c748d0b0d8.png">


demo包下为练习代码。

添加一个服务端和客户端交互的新功能步骤：
1. 创建控制台指令对应的 ConsoleCommand，并将其添加到 ConsoleCommandManager；
2. 在控制台输入指令和数据后，填入协议对应的指令数据包 XxxRequestPacket，将请求写到服务端；
3. 服务端创建对应的 XxxRequestHandler，并将其添加到服务端的 pipeline，处理完成后，构造对应的 XxxResponsePacket 发送给客户端；
4. 客户端创建对应的 XxxResponseHandler，并将其添加到客户端的 pipeline，完成响应的处理；
5. 最容易被忽略的，新增加的 XxxXxxPacket，需要完善编解码器 PacketCodec；
