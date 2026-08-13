2. postConnect() 和 think() 谁先被调用？为什么？
   postConnect() 先调用，think() 后调用。
   生命周期顺序可概括为：
   Connector.connect()
   ↓
   Agent 与 Kernel 建立连接
   ↓
   postConnect()
   ↓
   等待 Kernel 发送 KASense
   ↓
   processSense(KASense)
   ↓
   think(time, changed, heard)
   ↓
   用户实现的 abstract think()
   postConnect() 的作用
   postConnect() 是连接成功后的初始化回调。在其中会初始化后续思考过程依赖的运行环境，例如：
   this.ignoreTime = config
   .getIntValue(kernel.KernelConstants.IGNORE_AGENT_COMMANDS_KEY);

this.worldInfo = new WorldInfo(this.model);
然后确定运行模式：
if (!this.isPrecompute) {
if (this.precomputeData.isReady(this.worldInfo)) {
this.mode = ScenarioInfo.Mode.PRECOMPUTED;
} else {
this.mode = ScenarioInfo.Mode.NON_PRECOMPUTE;
}
}
并创建场景信息：
this.scenarioInfo = new ScenarioInfo(this.config, this.mode);
this.communicationModule = null;
因此，postConnect() 必须先完成。否则 think() 中需要使用的：
worldInfo
scenarioInfo
配置信息
预计算模式
ignoreTime
都还没有准备好。
think() 是如何触发的？
服务器每个时间步发送一个 KASense 感知消息。框架调用：
protected void processSense(KASense sense) {
int time = sense.getTime();
ChangeSet changed = sense.getChangeSet();

this.worldInfo.setTime(time);
this.model.merge(sense.getChangeSet());

Collection<Command> heard = sense.getHearing();
think(time, changed, heard);
}
也就是说，think() 不是连接完成时立即调用，而是：
Agent 先完成连接；
调用 postConnect() 初始化环境；
Kernel 开始发送每个 tick 的感知信息；
processSense() 收到感知信息；
再调用 think()。
需要注意两层 think()
Agent.java 中有两个相关方法：
@Override
protected void think(
int time,
ChangeSet changed,
Collection<Command> heard)
这是框架从 AbstractAgent 重写的入口，负责通信、信息更新和异常处理。
它内部再调用：
protected abstract void think();
第二个无参数 think() 才是具体 Agent 子类需要实现的业务决策逻辑。