package cn.tldream.ff.old.module;

import cn.tldream.ff.old.managers.resource.ResourceManager;

public class ResourceModule implements GameModule{
    private final String assetsPath;
    private ResourceManager resourceManager;

    public ResourceModule(String assetsPath) {
        this.assetsPath = assetsPath;
    }

    // 暴露服务接口
    public <T> T getAsset(String name, Class<T> type) {
        return resourceManager.get(name, type);
    }

    @Override
    public int getInitPriority() { return 100; } // 最高优先级

    @Override
    public void preInit() {
        this.resourceManager = new ResourceManager(assetsPath);
        resourceManager.loadConfiguration();
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void dispose() {
        resourceManager.dispose();
    }
}
