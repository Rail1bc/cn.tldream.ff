package cn.tldream.ff.old.module;

public interface GameModule {
    default int getInitPriority() { return 0; } // 初始化优先级
    default String[] getDependencies() { return new String[0]; } // 依赖模块

    void preInit();    // 预初始化（配置加载）
    void init();       // 主初始化
    void postInit();   // 后初始化（依赖注入）
    void update(float deltaTime);
    void dispose();
}
