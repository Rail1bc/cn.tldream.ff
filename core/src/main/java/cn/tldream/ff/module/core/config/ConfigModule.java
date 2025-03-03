package cn.tldream.ff.module.core.config;

import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.resource.ResourceModule;
import cn.tldream.ff.module.core.resource.descriptor.ResourceDescriptor;
import com.badlogic.gdx.Gdx;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
* 配置管理模块
* 依赖模块：无显式依赖，实际依赖实例化后的资源管理模块
* 生命周期：单例模式
* 实例化 程序启动时进行
* 依赖注入 |预初始化 |主初始化 |处置
* 模块管理器统一进行管理
* 工作内容：
* 管理配置管理模块的生命周期
* 提供全局访问，根据id获取资源描述符
* 工作流程：
* 初始化时，创建配置管理器、id与资源描述符的映射表
* 模块接受依赖注入后，将资源管理模块注入到配置管理器中
* 预初始化时，调用配置管理器的预初始化方法
* 主初始化时，调用配置管理器的初始化方法
* */


/*
* !!!!!!!! 注意 !!!!!!!!
* 配置管理模块，是唯一不符合规范的模块
* 因为配置加载依赖资源管理模块，而资源管理模块加载资源依赖配置管理模块
* 因此，配置管理器在实例化时，手动注册核心资源描述符
* 在依赖注入后，获取尚未完全初始化的资源管理模块
* 在预初始化阶段，调用资源管理模块，初始化配置管理模块
* */
public class ConfigModule implements GameModule {
    private static final String className = "配置管理模块";
    private static final Map<String, ResourceDescriptor> idMap = new ConcurrentHashMap<>(); // id与资源描述符的映射
    private static final Map<ConfigKey, Object> configMap = new ConcurrentHashMap<>();
    private static final ConfigModule instance = new ConfigModule(); // 单例
    private static final ConfigManager configManager = new ConfigManager(idMap, configMap); // 配置管理器
    private static ResourceModule resourceModule; // 资源管理模块
    private static boolean initialized = false; // 模块初始化状态

    /*
    * 单例模式
    * */

    /*私有构造函数*/
    private ConfigModule() {
        Gdx.app.debug(className, "实例化");
    }

    /*获取单例*/
    public static ConfigModule getInstance() {
        return instance;
    }


    /*
    * 暴露服务接口
    * */

    /*根据id获取资源描述符*/
    public ResourceDescriptor getResource(String id) {
        if(idMap.containsKey(id)) return idMap.get(id);
        return null;
    }

    /*根据枚举获取全局配置*/
    public <T> T getConfig(ConfigKey key) {
        return (T) configMap.get(key);
    }

    /*根据枚举设置对应全局配置*/
    public void setConfig(ConfigKey key, Object value) {
        configMap.put(key, value);
    }


    /*
     * 生命周期方法
     * 由模块管理器调用
     * */

    @Override
    public String[] getDependencies() {
        return new String[]{"resource"};
    }

    @Override
    public int getInitPriority(){
        return -1;
    }

    /*依赖注入*/
    @Override
    public void receiveDependency() {
        resourceModule = ModuleManager.getModule("resource", ResourceModule.class);
        configManager.receiveDependency(resourceModule);
    }

    /*预初始化*/
    @Override
    public void preInit() {
        Gdx.app.debug(className, "预初始化");
        configManager.preInit();
    }

    /*主初始化*/
    @Override
    public void init() {
        Gdx.app.debug(className, "主初始化");
        configManager.init(); // 初始化配置管理器
        initialized = true; // 设置模块初始化状态为true
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
