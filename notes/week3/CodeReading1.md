# CodeReading1 作业解答
## 1. SampleFireBrigade.java 的 sense() 方法做了什么？
`sense()` 方法的核心作用是：
1.  让消防队感知环境中的目标（比如火源）
2.  遍历所有目标，判断目标是否在消防队的感知范围内
3.  将在感知范围内的目标收集起来，作为后续行动的依据
    它的一般逻辑是：
- 获取所有目标列表
- 遍历每个目标，计算目标与消防队的距离
- 如果距离小于等于感知半径，就把目标加入到待处理的集合中
- 返回这些感知到的目标，供后续的行动方法（比如灭火、移动）使用
 ---
## 2. `for (Iterator it = targets.iterator(); it.hasNext();)` 这种语法叫什么？为什么这里不用增强 for 循环？
### 语法名称
这种语法叫做 **迭代器遍历（Iterator 循环）**，是 Java 中遍历集合的一种经典方式。
### 为什么不用增强 for 循环？
增强 for 循环（`for (Target t : targets)`）本质上也是基于迭代器实现的，但在以下场景中，直接使用 `Iterator` 更合适：
1.  **遍历中需要删除元素**
    - 如果在增强 for 循环里调用 `targets.remove()`，会抛出 `ConcurrentModificationException` 并发修改异常
    - 而使用 `Iterator` 的 `it.remove()` 方法，可以安全地在遍历过程中删除元素
2.  **需要更灵活的遍历控制**
    - 比如需要跳过某些元素、多次遍历、或者在遍历中修改集合结构时，`Iterator` 提供了 `hasNext()`、`next()` 等更底层的控制方法
3.  **兼容旧版本 Java**
    - 增强 for 循环是 Java 5 才引入的，而 `Iterator` 从 Java 1.2 就存在，兼容性更好
      在 `SampleFireBrigade.java` 这类场景中，很可能会在遍历目标列表时过滤掉不符合条件的目标（比如已经被处理的目标），所以使用 `Iterator` 遍历是更安全、更合适的选择。