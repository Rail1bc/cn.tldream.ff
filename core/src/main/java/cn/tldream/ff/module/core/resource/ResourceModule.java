package cn.tldream.ff.module.core.resource;

import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.descriptor.ResourceDescriptor;
import com.badlogic.gdx.Gdx;

/**
 * 资源管理模块
 * 依赖模块：配置管理模块
 * 生命周期：
 * 实例化
 * 依赖注入
 * 预初始化
 * 主初始化
 * 后初始化
 * 工作内容：
 *
 * */
public class ResourceModule implements GameModule {
    private final String className = "资源管理模块";
    private final ResourceManager resourceManager; //资源管理器
    private final ConfigModule configModule = ConfigModule.getInstance(); // 配置管理模块
    private boolean isInitialized = false; // 初始化状态

    public ResourceModule(String assetsPath) {
        resourceManager = new ResourceManager(assetsPath);
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

    /*通过资源id，阻塞同步加载并获取资源*/
    public synchronized <T> T loadAndGet(String id){
        ResourceDescriptor resd = configModule.getResource(id);
        resourceManager.load(resd.getPath(),resd.getType());
        resourceManager.finishLoading();
        return resourceManager.get(resd.getPath(),resd.getType());

    }

    /*更新，继续加载*/
    public boolean update(){
        Gdx.app.debug(className, "更新");
        return resourceManager.update();
    }

    @Override
    public String[] getDependencies() {
        return new String[]{"config"}; // 依赖配置管理模块
    }

    /*初始化*/

    @Override
    public void preInit(){
        Gdx.app.debug(className, "预初始化");
    }

    @Override
    public void init() {
        Gdx.app.debug(className, "主初始化");
        resourceManager.init();
    }

    @Override
    public void postInit() {
        Gdx.app.debug(className, "后初始化");
        isInitialized = true;
    }

    /*处置*/
    @Override
    public void dispose() {
        resourceManager.dispose();
    }


    @Override
    public boolean isInitialized() {
        return isInitialized;
    }
}
