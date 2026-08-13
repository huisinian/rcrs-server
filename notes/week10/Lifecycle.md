# Agent完整启动调用链
1. Main.java 的 main()：程序入口，解析命令行传入参数。
2. 进入 AgentLauncher.java：
   ① initConnector()：注册全部6种通信Connector连接器。
   ② start()：启动TCP组件，执行 connector.connect()，每一类Connector开启独立线程，尝试连接rcrs‑server仿真服务器。
3. 连接服务器成功，进入 Agent.java：
   ① postConnect() 回调函数执行：Agent和服务器建立连接后执行，初始化WorldInfo、ScenarioInfo、AgentInfo，设置运行模式。
   ② processSense() 每一个仿真tick被回调，更新世界状态信息。
   ③ 调用 think(time, changed, heard)。
   ④ think内部：首个tick初始化通信模块、订阅消息频道，调用抽象abstract think()编写业务决策；
   ⑤ send(Command) 将机器人行动指令发送给仿真服务端。
