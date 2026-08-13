1. initConnector() 注册了哪 6 种 Connector？
   AgentLauncher.initConnector() 注册了两组共 6 种 Connector：
   分类	Connector	对应角色	作用
   Platoon 行动单位	ConnectorAmbulanceTeam	Ambulance Team，救护队员	搜救、挖掘、装载并运送伤员
   Platoon 行动单位	ConnectorFireBrigade	Fire Brigade，消防队员	灭火、补水以及处理火灾现场
   Platoon 行动单位	ConnectorPoliceForce	Police Force，警察队员	清除路障，为其他救援单位打通道路
   Office 指挥中心	ConnectorAmbulanceCentre	Ambulance Centre，救护中心	协调救护队、分配救援目标
   Office 指挥中心	ConnectorFireStation	Fire Station，消防中心	协调消防队、分配灭火目标
   Office 指挥中心	ConnectorPoliceOffice	Police Office，警察中心	协调警察部队、分配清障目标