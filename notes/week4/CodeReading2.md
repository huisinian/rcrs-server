# CodeReading2

## 1. 为什么 Agent 要声明为 abstract？

- `Agent` 是一个**抽象基类**，它定义了所有智能体（Agent）的通用行为框架（如 `think()`、`update()` 等方法），但不包含具体的业务实现。
- 抽象类不能被直接实例化，只能被继承，这样可以强制所有子类（如 `Platoon`、`PlatoonFire`）必须实现 `Agent` 中定义的抽象方法，保证子类行为的规范性。
- 它封装了公共属性和逻辑（如位置、状态等），让不同类型的 Agent 复用这些代码，同时又允许子类根据自身业务重写具体方法，符合面向对象中“开闭原则”的设计思想。

## 2. Tactics.java 中的 think() 方法和 Agent.java 中的 think() 方法是什么关系？

- 它们是**重载/实现（Override）关系**，更准确地说：
    1.  `Agent` 类中的 `think()` 通常是一个抽象方法或模板方法，定义了智能体“思考”的行为接口。
    2.  `Tactics` 类（或其子类）中的 `think()` 方法，是对 `Agent` 中 `think()` 方法的**重写（Override）**，提供了具体的战术决策逻辑实现。
    3.  从设计模式的角度看，`Agent` 定义了行为的框架，而 `Tactics` 系列类则是注入具体的策略实现，让 Agent 可以灵活切换不同的战术逻辑。

## 3. 什么叫"策略模式"？在本项目中体现在哪里？

### 策略模式定义
策略模式（Strategy Pattern）是一种行为型设计模式，它定义了一系列算法（策略），并将每个算法封装起来，使它们可以互相替换，让算法的变化独立于使用算法的客户端。
核心要素：
- 抽象策略角色：定义策略的公共接口（如 `Tactics` 接口/抽象类）
- 具体策略角色：实现抽象策略的具体算法（如 `DefaultTacticsFireBrigade`）
- 环境角色：持有策略的引用，通过策略接口调用算法（如 `Agent`/`Platoon`）

### 在本项目中的体现
- 抽象策略：`Tactics` 类，定义了 `think()` 等战术决策的接口。
- 具体策略：`DefaultTacticsFireBrigade` 类，实现了具体的灭火战术逻辑。
- 环境角色：`Agent`/`Platoon` 类，持有 `Tactics` 的引用，通过调用 `tactics.think()` 来执行战术，而不需要关心具体是哪一种战术实现。
- 好处：如果后续要新增其他战术（如 `TacticsRescue`、`TacticsDefend`），只需要新增 `Tactics` 的子类，不需要修改 `Agent` 或 `Platoon` 的代码，实现了“对扩展开放，对修改关闭”。

---

## 补充：继承和接口的区别 & 项目例子

### 继承（extends）
- 是类与类之间的“is-a”关系，一个类只能继承一个父类（单继承）。
- 子类可以继承父类的属性和方法，也可以重写父类的方法。
- 项目例子：`Platoon extends Agent`、`PlatoonFire extends Platoon`，`DefaultTacticsFireBrigade extends Tactics`。

### 接口（implements）
- 是类与接口之间的“like-a”关系，一个类可以实现多个接口。
- 接口中只能定义常量和抽象方法（Java 8 前），实现类必须实现所有抽象方法。
- 接口主要用于定义行为规范，不包含具体实现。