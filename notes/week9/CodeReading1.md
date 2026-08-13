启动先后顺序
startkernel：先启动 rcrs 仿真内核，加载读取kernel.cfg配置，初始化仿真底层。
startSim：启动仿真模拟器，载入场景、地图，开启 TCP 通信端口，等待智能体 agent 来连接。
startViewer：启动可视化查看器，用来显示仿真画面。
startViewerEventLogger：启动事件日志记录器，记录仿真运行产生的各类日志。