一、adf.core —— 框架核心（抽象定义 / 接口层）
adf.core
程序入口。Main.java 解析命令行参数（含别名展开，如 -local、-all 等），然后创建并启动 AgentLauncher。
adf.core.agent —— Agent 基类
Agent.java，所有智能体（救护/消防/警察部队与中心）的抽象基类，定义智能体生命周期的统一骨架。
adf.core.agent.action —— 行动抽象
Action.java：智能体每个时间片决策后返回的"行动"基类。
action.ambulance：救护队专属行动 —— 装载伤员（ActionLoad）、救援（ActionRescue）、卸载（ActionUnload）
action.common：所有智能体通用行动 —— 移动（ActionMove）、待命恢复（ActionRest）
action.fire：消防队专属行动 —— 灭火（ActionExtinguish）、加水（ActionRefill）、救援（ActionRescue）
action.police：警察专属行动 —— 清除路障（ActionClear）
adf.core.agent.communication —— 通信层
MessageManager.java：管理消息的收/发、频道订阅等。
communication.standard：标准通信模块实现（StandardCommunicationModule）standard.bundle：标准消息包体系 —— 消息基类（StandardMessage）、消息打包工具（MessageUtil）、消息协调器（StandardMessageCoordinator）、优先级（StandardMessagePriority）及占位消息（MessageDummy）bundle.centralized：集中式指挥风格的消息 —— 救护/消防/警察/侦察指令（Command*）与状态报告（MessageReport）
bundle.information：信息类消息 —— 上报建筑、道路、平民、救护队、消防队、警察部队等实体状态（MessageBuilding、MessageRoad、MessageCivilian、MessageAmbulanceTeam、MessageFireBrigade、MessagePoliceForce）
bundle.topdown：自上而下（中央下达）指令风格的消息，与 centralized 对应的精简版 Command*/MessageReport


adf.core.agent.config
ModuleConfig.java：模块配置信息（各模块在配置文件中的键值）。
adf.core.agent.develop
DevelopData.java：开发/调试模式专用数据容器，用于开发模式下保存调试信息。
adf.core.agent.info
智能体可获取的世界信息快照：AgentInfo（自身状态）、WorldInfo（世界模型，道路/建筑/实体等，37KB 的大类）、ScenarioInfo（场景配置）。
adf.core.agent.module
ModuleManager.java：模块管理器，负责注册/获取各类功能模块（算法、复杂决策等），是智能体的"模块中枢"。
adf.core.agent.office
中心类智能体（决策中心）：抽象 Office 及三个子类 OfficeAmbulance/OfficeFire/OfficePolice（救护中心、消防站、警察局）。
adf.core.agent.platoon
行动类智能体（小队）：抽象 Platoon 及 PlatoonAmbulance/PlatoonFire/PlatoonPolice（救护队、消防队、警察部队）。
adf.core.agent.precompute
预计算机制：PrecomputeData（预计算数据容器）与 PreData（接口），用于仿真开始前离线计算/复用的数据。
adf.core.component —— 组件抽象层
AbstractLoader.java：组件加载器抽象基类（按类名加载具体组件实现）。
component.centralized
集中式指挥组件：CommandPicker（中心挑选目标下发指令）与 CommandExecutor（小队执行收到的指令）。
component.communication
通信组件接口：CommunicationModule、CommunicationMessage、MessageBundle、MessageCoordinator、ChannelSubscriber。
communication.util：通信底层位流工具 —— BitOutputStream/BitStreamReader（消息压缩为位流）。
component.extaction
ExtAction.java：扩展行动组件（比普通 Action 更复杂、可跨时间片执行的动作，如清障、灭火、运送）。
component.module
AbstractModule.java：功能模块抽象基类，定义 precompute/resume/preparate/updateInfo/calc 生命周期钩子，支持子模块注册。
module.algorithm：算法模块抽象 —— PathPlanning（路径规划）、Clustering（聚类）及其静态/动态变体。
module.complex：复杂决策模块抽象 —— 目标检测器（Building/Human/RoadDetector）、目标选择器（Building/Human/RoadSelector）、目标分配器（Ambulance/Fire/PoliceTargetAllocator 与 TargetAllocator 基类）、区域搜索（Search）、通用 TargetDetector/TargetSelector。
component.tactics
Tactics.java：战术组件抽象，是智能体决策逻辑的核心，把模块组织起来并在 think() 中输出 Action；含六种类型的战术接口（TacticsAmbulanceCentre/Team、TacticsFireStation/Brigade、TacticsPoliceOffice/Force）和中心基类 TacticsCenter。
adf.core.debug
调试辅助：DefaultLogger（日志）、WorldViewer/WorldViewLauncher（可视化查看仿真世界，供调试用）。
adf.core.launcher —— 启动与配置层
AgentLauncher.java：核心启动器，负责装配加载器、连接服务器、实例化 Agent。
ConfigInitializer / ConfigKey / ConsoleOutput：配置初始化、配置键定义、控制台输出。
launcher.annotation：注解 NoStructureWarning（抑制结构警告）。
launcher.connect：连接器 Connector 及六个子类（ConnectorAmbulanceCentre/Team、ConnectorFireStation/Brigade、ConnectorPoliceOffice/Force），负责与 RoboCup 仿真服务器的通信协议对接。
launcher.option：命令行选项定义 —— 基类 Option 及一堆选项类（OptionHost/Server/Team/TeamName/Debug/Develop/Precompute/ModuleConfig/ModuleData 等）。
launcher.dummy：哑实现（占位/测试用）dummy.tactics：三个小队哑战术（DummyTacticsAmbulanceTeam/FireBrigade/PoliceForce）
dummy.tactics.center：三个中心哑战术（DummyTacticsAmbulanceCentre/FireStation/PoliceOffice）

二、adf.impl —— 默认实现层
adf.impl
DefaultLoader.java：默认组件加载器，负责把 adf.core 中的抽象类映射到 impl 下的默认实现类。
adf.impl.centralized
集中式指挥的默认实现：DefaultCommandPicker*（救护/消防/警察的默认指令挑选）与 DefaultCommandExecutor*（含 Scout 侦察、ScoutPolice 等指令执行器）。
adf.impl.extaction
扩展行动的默认实现：DefaultExtActionMove（移动）、DefaultExtActionClear（清障）、DefaultExtActionFireFighting（灭火）、DefaultExtActionFireRescue（火灾救援）、DefaultExtActionTransport（运送伤员）。
adf.impl.module —— 默认模块实现
module.algorithm：默认算法实现 —— AStarPathPlanning、DijkstraPathPlanning（路径规划）、KMeansClustering（K-Means 聚类）、FireClustering（火灾聚类）
module.comm：默认通信实现 —— DefaultChannelSubscriber（频道订阅）、DefaultMessageCoordinator（消息协调）
module.complex：默认复杂模块实现 —— DefaultBuildingDetector/DefaultHumanDetector/DefaultRoadDetector（检测器）、DefaultAmbulanceTargetAllocator/DefaultFireTargetAllocator/DefaultPoliceTargetAllocator（目标分配）、DefaultSearch（搜索）
adf.impl.tactics
默认战术实现：DefaultTacticsAmbulanceCentre/DefaultTacticsAmbulanceTeam/DefaultTacticsFireStation/DefaultTacticsFireBrigade/DefaultTacticsPoliceOffice/DefaultTacticsPoliceForce（六种角色各一个可直接运行的默认战术）。
tactics.utils：战术层工具 MessageTool（消息编解码与解析工具）。