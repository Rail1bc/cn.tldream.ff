package cn.tldream.ff.module.core.resource;

import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.descriptor.ResourceDescriptor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

import java.util.Collection;
import java.util.HashMap;

/*
* 资源管理模块
* 依赖模块：配置管理模块
* 生命周期：由主类实例化，并注册进模块管理器
* 实例化 由主类进行
* 依赖注入 |预初始化 |主初始化 |处置
* 模块管理器统一进行管理
* 工作内容：
* 提供异步加载资源的功能
* 可查看加载进度
* 可同步加载
* 工作流程：
* 调用 load()方法，通过id 获取资源描述，再通过资源管理器加载资源
* 调用 finishLoading()方法，阻塞等待加载完成
* 调用 get()方法，获取资源
* */
public class ResourceModule implements GameModule {
    private final String className = "资源管理模块";
    private final ResourceManager resourceManager; //资源管理器
    private ConfigModule configModule; // 配置管理模块
    private boolean isInitialized = false; // 初始化状态

    /*
     * 生命周期方法
     * 由模块管理器调用
     * */

    /*构造函数*/
    public ResourceModule(String assetsPath) {
        Gdx.app.debug(className, "实例化");
        resourceManager = new ResourceManager(assetsPath);
    }

    /*获取依赖*/
    @Override
    public String[] getDependencies() {
        return new String[]{"config"}; // 依赖配置管理模块
    }

    /*依赖注入*/
    @Override
    public void receiveDependency() {
        configModule = ModuleManager.getModule("config", ConfigModule.class);
        resourceManager.setConfigModule(configModule);
    }

    /*预初始化*/
    @Override
    public void preInit(){
        Gdx.app.debug(className, "预初始化");
        resourceManager.preInit();
    }

    /*主初始化*/
    @Override
    public void init() {
        Gdx.app.debug(className, "主初始化");
        resourceManager.init();
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


    /*
     * 暴露服务接口
     * */

    /**将资源加入加载队列
     * @param id 资源id
     * */
    public void load(String id){
        ResourceDescriptor resd = configModule.getResource(id);
        resourceManager.load(resd.getPath(),resd.getType());
    }

    /**
     * 批量将资源加入加载队列
     * @param ids 资源id数组
     * */
    public void loadBatch(Collection<String> ids){
        ids.forEach(this::load);
    }

    /**
     * 获取资源
     * @param id 资源id
     * @return 资源
     * */
    public synchronized <T> T get (String id) {
        ResourceDescriptor resd = configModule.getResource(id);
        return resourceManager.get(resd.getPath(),resd.getType()) ;
    }

    /**
     * 批量获取资源
     * @param ids 资源id数组
     * @return 资源id与资源的映射
     * */
    public synchronized <T> HashMap<String, T> getBatch (Collection<String> ids) {
        HashMap<String, T> map = new HashMap<>();
        ids.forEach(id -> {map.put(id, this.get(id));});
        return map;
    }

    /**
     * 更新，继续加载
     * */
    public boolean update(){
        Gdx.app.debug(className, "更新");
        return resourceManager.update();
    }

    /**
     * 同步加载已经在加载队列的资源，阻塞
     * */
    public void finishLoading(){
        resourceManager.finishLoading();
    }

    /**
     * 同步加载并获取资源,阻塞
     * @param id 资源id
     * @return 资源
     * */
    public synchronized <T> T loadAndGet(String id){
        ResourceDescriptor resd = configModule.getResource(id);
        resourceManager.load(resd.getPath(),resd.getType());
        resourceManager.finishLoading();
        return resourceManager.get(resd.getPath(),resd.getType());

    }

    public void setParameterFillName(String id,int size){
        resourceManager.setParameter(id,size);
    }


}
