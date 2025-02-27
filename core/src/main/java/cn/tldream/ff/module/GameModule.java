package cn.tldream.ff.module;

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
