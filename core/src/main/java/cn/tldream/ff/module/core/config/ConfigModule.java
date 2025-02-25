package cn.tldream.ff.module.core.config;

import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.resource.ResourceModule;

public class ConfigModule implements GameModule {
    private static ConfigModule instance;
    private ConfigManager configManager;
    private ResourceModule resourceModule;
    private boolean initialized = false;

    public static ConfigModule getInstance() {
        if (instance == null) {
            instance = new ConfigModule();
        }
        return instance;
    }

    @Override
    public String[] getDependencies() {
        return new String[]{"resource"}; // 依赖资源模块
    }

    @Override
    public void receiveDependency(String name, GameModule module) {
        if ("resource".equals(name)) {
            resourceModule = (ResourceModule) module;
        }
    }

    @Override
    public void init() {
        configManager = new ConfigManager(resourceModule);
        configManager.init();
        initialized = true;
    }

    @Override
    public void dispose() {
        configManager.dispose();
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
}
