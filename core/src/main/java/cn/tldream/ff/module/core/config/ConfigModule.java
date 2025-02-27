package cn.tldream.ff.module.core.config;

import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.resource.ResourceModule;
import cn.tldream.ff.module.core.resource.descriptor.ResourceDescriptor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Map;

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
    private static final ConfigModule instance = new ConfigModule(); // 单例
    private static final ConfigManager configManager = new ConfigManager();; // 配置管理器
    private static ResourceModule resourceModule; // 资源管理模块
    private static boolean initialized = false; // 模块初始化状态

    public static ConfigModule getInstance() {
        return instance;
    }

    private ConfigModule() {
        Gdx.app.log(className, "实例化");
    }

    /*暴露服务接口*/
    public ResourceDescriptor getResource(String id) {
        return configManager.getResource(id);
    }

    /*获取idMap*/
    public Map<String, ResourceDescriptor> getIdMap() {
        return configManager.getIdMap();
    }

    /*依赖注入*/
    @Override
    public void receiveDependency() {
        resourceModule = ModuleManager.getModule("resource", ResourceModule.class);
        configManager.setResourceModule(resourceModule);
    }

    /*预初始化*/
    @Override
    public void preInit() {
        Gdx.app.log(className, "预初始化");
        configManager.preInit();
    }

    /*主初始化*/
    @Override
    public void init() {
        Gdx.app.log(className, "主初始化");
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

    private void loadConfig(JsonValue jsonValue) {
        configManager.loadConfig(jsonValue);
    }
}
