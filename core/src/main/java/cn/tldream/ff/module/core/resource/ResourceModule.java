package cn.tldream.ff.module.core.resource;

import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.descriptor.ResourceDescriptor;
import com.badlogic.gdx.Gdx;

/**
 * 资源管理模块
 * 依赖模块：配置管理模块
 * 生命周期：
 * 模块管理器初始化时，初始化资源管理模块
 * 持有资源管理类实例
 * 资源管理类功能固定后，不再暴露资源管理类
 * */
public class ResourceModule implements GameModule {
    private final String className = "资源管理模块";
    private final ResourceManager resourceManager;
    private ConfigModule configModule;
    private boolean isInitialized = false;

    public ResourceModule(String assetsPath) {
        resourceManager = new ResourceManager(assetsPath);
        configModule = ConfigModule.getInstance();
    }

    /*暴露服务接口*/
    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    /*同步加载，阻塞*/
    public void finishLoading(){
        resourceManager.finishLoading();
    }

    /*通过资源id，将资源加入加载队列*/
    public <T> void load(String id){
        ResourceDescriptor resd = configModule.getResource(id);
        resourceManager.load(resd.getPath(),resd.getType());
    }

    /*通过资源id，获取资源*/
    public synchronized <T> T get (String id) {
        ResourceDescriptor resd = configModule.getResource(id);
        return resourceManager.get(resd.getPath(),resd.getType());
    }

    public synchronized <T> T loadAndGet(String id){
        ResourceDescriptor resd = configModule.getResource(id);
        resourceManager.load(resd.getPath(),resd.getType());
        resourceManager.finishLoading();
        return resourceManager.get(resd.getPath(),resd.getType());

    }

    /*更新，继续加载*/
    public boolean update(){
        Gdx.app.log(className, "更新");
        return resourceManager.update();
    }

    @Override
    public int getInitPriority() { return 100; } // 最高优先级


    @Override
    public void init() {
        Gdx.app.log(className, "初始化");
        resourceManager.initialize();
        isInitialized = true;
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
