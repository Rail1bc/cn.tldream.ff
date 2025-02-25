package cn.tldream.ff.module.core.resource;

import cn.tldream.ff.module.GameModule;

/**
 * 资源管理器模块
 * 核心模块中的核心模块，不依赖任何模块
 * 持有资源管理类实例
 * 资源管理类功能固定后，不再暴露资源管理类
 * */
public class ResourceModule implements GameModule {
    private final ResourceManager resourceManager;
    private boolean isInitialized = false;

    public ResourceModule(String assetsPath) {
        resourceManager = new ResourceManager(assetsPath);
    }

    /*暴露服务接口*/
    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    @Override
    public int getInitPriority() { return 100; } // 最高优先级


    @Override
    public void init() {
        resourceManager.initialize();
        isInitialized = true;
    }

    public boolean update(){
        return resourceManager.update();
    }

    @Override
    public void dispose() {
        resourceManager.dispose();
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }
}
