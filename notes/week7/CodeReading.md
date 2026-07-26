1.stream().map(...).collect(...) 这段代码做了什么？
stream().map(connector -> new Thread(() -> {})).collect(Collectors.toList())
stream()：把 Connector 集合转为流；
map()：遍历每一个 Connector 对象，为每一个 Connector 新建一个独立 Thread 线程；
collect(Collectors.toList())：把创建出来的所有 Thread 收集，存入 List 集合保存。
2.为什么要给每个 Connector 启动独立线程？串行连接会有什么问题？
开启独立线程的原因
多个 Connector 需要同时和服务端建立连接、通信。每个线程独立运行，实现并发连接，多个 Connector 同步工作，互不阻塞。
串行连接的缺陷
串行模式下，必须等上一个 Connector 完整连接、通信结束后，才能启动下一个。
运行速度极慢；
无法实现多个客户端同时在线；
前面的连接如果发生阻塞、等待，后面所有任务全部卡住。