3. 如果 think() 内抛出异常，会怎么样？
   这里需要区分：源码捕获的是无参数的业务层 think() 抛出的 Exception：
   try {
   think();
   } catch (Exception e) {
   e.printStackTrace();
   }
   因此，如果具体 Agent 实现的 think() 抛出异常：
   异常会被捕获；
   调用 e.printStackTrace() 将堆栈打印到控制台；
   异常不会继续向上传播；
   当前 Agent 线程通常不会因为这个异常立即终止；
   当前时间步仍会继续执行后面的消息协调和发送流程。
   异常捕获后，代码仍然执行：
   this.messageManager.coordinateMessages(
   this.agentInfo,
   this.worldInfo,
   this.scenarioInfo);

this.communicationModule.send(this, this.messageManager);
所以流程是：
业务 think()
↓ 抛出 Exception
catch
↓ 打印异常堆栈
coordinateMessages()
↓
communicationModule.send()
对当前 tick 的实际影响
虽然 Agent 不一定崩溃，但业务决策没有正常完成，因此当前 tick 可能出现：
没有生成有效行动；
没有发送移动、灭火、救援或清障指令；
决策状态只更新了一部分；
之前已经加入 MessageManager 的消息仍可能被协调并发送；
下一个 tick 到来时，Agent 一般还会继续执行。
因此，这是一种“记录错误并尽量继续运行”的容错策略，而不是让整个 Agent 因一次决策异常直接退出。
捕获范围的限制
try-catch 只包住这一句：
think();
它没有包住整个 think(int, ChangeSet, Collection<Command>)。所以如果异常发生在下面这些位置，则不会被这里捕获：
this.agentInfo.recordThinkStartTime();
this.messageManager.subscribe(...);
this.communicationModule.receive(...);
this.messageManager.coordinateMessages(...);
this.communicationModule.send(...);
另外，捕获类型是：
catch (Exception e)
因此它能捕获常见运行时异常，例如：
NullPointerException
IllegalArgumentException
IndexOutOfBoundsException
但不能捕获不属于 Exception 的严重 Error，例如：
OutOfMemoryError
StackOverflowError
AssertionError