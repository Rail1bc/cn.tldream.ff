package cn.tldream.ff.module;

/**
 * 游戏模块接口
 * 所有的模块都需要实现此接口
 * 实现此接口后，需要在模块管理器中注册
 * 模块管理器将管理实现类的生命周期
 * 实现类的初始化应遵循以下规则：
 *      1. 需要重写:接口方法getDependencies(),返回依赖模块的注册名称
 *      2. 需要重写:接口方法receiveDependency()进行依赖注入
 *      3. 在依赖注入阶段，receiveDependency()方法中应注意：
 *          - 通过模块管理器获取依赖模块实例
 *          - 如果依赖关系是单向的，只能保证注入的依赖模块已经完成了依赖注入
 *          - 如果依赖关系是双向的、循环的，只能保证注入的依赖模块已经完成了实例化
 *          - 注入的依赖模块此时可能尚不具备工作能力
 *      4. 在预初始化阶段，preInit()方法中应注意：
 *          核心职责：
 *          - 加载私有资源配置
 *          - 初始化非依赖型组件
 *          - 准备基础数据
 *
 *          依赖保证：
 *          - 所有依赖模块至少完成 依赖注入 receiveDependency()
 *          - 单向依赖模块至少完成 预初始化 preInit()
 *          - 配置管理模块可提供全部原版资源的资源描述符
 *          - 资源管理模块可加载全部原版资源
 *
 *          允许操作：
 *          - 访问依赖模块的配置数据
 *          - 调用资源管理模块方法加载资源
 *
 *          禁止操作：
 *          - 调用依赖模块的业务方法
 *      5. 在主初始化阶段，init()方法中应注意：
 *          核心职责：
 *          - 初始化核心业务逻辑
 *
 *          依赖保证：
 *          - 所有依赖模块至少完成 预初始化 preInit()
 *          - 单向依赖模块至少完成 主初始化 init()
 *          - 配置管理模块可提供全部原版资源的资源描述符
 *          - 资源管理模块可加载全部原版资源
 *
 *          允许操作：
 *          - 使用依赖模块的公共服务
 *
 *          禁止操作：
 *          - 执行跨模块业务流程
 *      6. 在后初始化阶段，postInit()方法中应注意：
 *          核心职责：
 *          - 执行跨模块协同
 *          - 注册全局钩子
 *          - 启动最终业务流程
 *
 *          依赖保证：
 *          - 所有依赖模块至少完成 主初始化 init()
 *          - 单向依赖模块至少完成 后初始化 postInit()
 *
 *          允许操作：
 *          - 调用任意模块的公共接口
 *          - 触发跨模块业务流程
 *          - 修改全局状态
 * */
public interface GameModule {
    default int getInitPriority() { return 0; } // 初始化优先级
    default String[] getDependencies() { return new String[0]; } // 依赖模块
    default void receiveDependency() {} // 依赖注入

    default void preInit() {}    // 预初始化（资源配置等）
    void init();                 // 主初始化（业务逻辑）
    default void postInit() {}   // 后初始化（跨模块交互）
    default boolean isEnabled() { return true; } // 是否启用
    default boolean isInitialized() { return false; } // 是否初始化
    void dispose(); // 释放

}
