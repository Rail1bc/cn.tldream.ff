package cn.tldream.ff.module.core.resource;

import cn.tldream.ff.module.GameModule;

public class ResourceModule implements GameModule {
    private final ResourceManager resourceManager;

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
    }

    public boolean update(){
        return resourceManager.update();
    }

    @Override
    public void dispose() {
        resourceManager.dispose();
    }
}
