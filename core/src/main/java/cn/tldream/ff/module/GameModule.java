package cn.tldream.ff.module;

/**
 * 游戏模块接口
 * 所有的模块都需要实现此接口
 * 实现此接口后，需要在模块管理器中注册
 * 模块管理器将管理实现类的生命周期
 * 实现类应遵循以下规则：
 *      1. 需要重写:接口方法getDependencies(),返回依赖模块的注册名称
 *      2. 需要重写:接口方法receiveDependency()进行依赖注入
 *      3. 在依赖注入阶段，receiveDependency()方法中应注意：
 *          1. 通过模块管理器获取依赖模块实例
 *          2. 如果依赖关系是单向的，只能保证注入的依赖模块已经完成了依赖注入
 *          3. 如果依赖关系是双向的、循环的，只能保证注入的依赖模块已经完成了实例化
 *          4. 注入的依赖模块此时可能尚不具备工作能力
 *      4. 在预初始化阶段，preInit()方法中应注意：
 *          1. 最好不调用依赖模块的功能
 *          2. 如果模块双向、循环依赖，可以考虑调用依赖模块部分功能(确保调用的功能，在对应模块完成依赖注入后即可正常工作)
 *          3. 应确保
 *      5. 在主初始化阶段，init()方法中应注意：
 *          1. 最好不调用其他模块的功能
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
