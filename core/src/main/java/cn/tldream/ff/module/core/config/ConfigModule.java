package cn.tldream.ff.module.core.config;

import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.resource.ResourceModule;
import cn.tldream.ff.module.core.resource.descriptor.ResourceDescriptor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置管理模块
 * 依赖模块：资源管理模块
 * 生命周期：单例模式
 * 模块管理器进行依赖排序时依赖注入
 * 在资源管理模块初始化后进行初始化
 * 模块管理器销毁时统一销毁
 * 工作内容：
 * 加载配置文件、资源id与资源路径的映射关系
 * 提供全局的访问接口
 * 工作流程：
 * 模块接受依赖注入后，创建配置管理器
 * 初始化配置管理器，设置模块初始化状态为true
 * */
public class ConfigModule implements GameModule {
    private static final String className = "配置管理模块";
    private static ConfigModule instance; // 单例
    private ConfigManager configManager; // 配置管理器
    private ResourceModule resourceModule; // 资源管理模块
    private boolean initialized = false; // 模块初始化状态

    public static ConfigModule getInstance() {
        if (instance == null) {
            instance = new ConfigModule();
        }
        return instance;
    }

    /*暴露服务接口*/
    public ResourceDescriptor getResource(String id) {
        return configManager.getRes(id);
    }

    /*获取idMap*/
    public Map<String, ResourceDescriptor> getIdMap() {
        return configManager.getIdMap();
    }

    @Override
    public String[] getDependencies() {
        return new String[]{"resource"}; // 依赖资源模块
    }

    /*依赖注入*/
    @Override
    public void receiveDependency(String name, GameModule module) {
        if ("resource".equals(name)) {
            resourceModule = (ResourceModule) module;
        }
        configManager = new ConfigManager(resourceModule); // 创建配置管理器
        loadConfig(resourceModule.loadAndGet("vanilla:core")); // 读取核心配置

        loadConfig(resourceModule.loadAndGet("vanilla:config.resources.font")); // 读取字体资源配置
        loadConfig(resourceModule.loadAndGet("vanilla:config.resources.skin")); // 读取皮肤资源配置
        loadConfig(resourceModule.loadAndGet("vanilla:config.resources.texture")); // 读取纹理资源配置


        for(Map.Entry<String , ResourceDescriptor> entry : configManager.getIdMap().entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue().getPath() );
        }
    }

    private void loadConfig(JsonValue jsonValue) {
        configManager.loadConfig(jsonValue);
    }

    /*初始化*/
    @Override
    public void init() {
        Gdx.app.log(className, "初始化");
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
