package cn.tldream.ff.module.core.config;

import cn.tldream.ff.module.core.resource.ResourceModule;
import cn.tldream.ff.module.core.resource.descriptor.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Map;


/*
* 配置管理器
* 依赖模块：无显式依赖，实际依赖实例化后的资源管理模块
* 生命周期：由配置管理模块实例化并管理
* 实例化 模块实例化时进行
* 依赖注入 模块依赖注入时进行
* 预初始化 模块预初始化时进行  过程依赖实例化后的资源管理模块
* 主初始化 模块主初始化时进行
* 处置 模块处置时进行
* 工作内容：
* 读取配置文件、资源配置文件
* 解析文件，维护资源id与资源描述符的映射关系
* 工作流程：
* 实例化时，获取idMap引用，并注入核心配置文件相对路径
* 依赖注入时，获取实例化后的资源管理模块实例
* 预初始化时，通过获取的资源管理模块实例读取核心配置文件
* 加载配置文件、资源配置文件，并维护资源id与资源描述符的映射关系
* */
public class ConfigManager implements Disposable{
    private final String className = "配置管理器";
    private final Map<String, ResourceDescriptor> idMap; // id与资源描述符的映射
    private ResourceModule resourceModule; // 资源管理模块实例


    /*
    * 生命周期方法
    * 由配置管理模块调用
    * */

    /*构造函数*/
    public ConfigManager(Map<String, ResourceDescriptor> idMap) {
        Gdx.app.log(className, "实例化");
        this.idMap = idMap; // 注入idMap
        idMap.put("vanilla:core", new JsonDes("core.json")); // 配置文件，全局唯一硬编码的相对路径
    }

    /*依赖注入*/
    public void setResourceModule(ResourceModule resourceModule) {
        this.resourceModule = resourceModule; // 注入资源管理模块实例
    }

    /*预初始化*/
    public void preInit() {
        Gdx.app.log(className, "预初始化");
        loadConfig(resourceModule.loadAndGet("vanilla:core")); // 读取核心配置
        loadConfig(resourceModule.loadAndGet("vanilla:config.core.overall"));// 读取全局配置
        loadConfig(resourceModule.loadAndGet("vanilla:config.resources.font")); // 读取字体资源配置
        loadConfig(resourceModule.loadAndGet("vanilla:config.resources.skin")); // 读取皮肤资源配置
        loadConfig(resourceModule.loadAndGet("vanilla:config.resources.texture")); // 读取纹理资源配置
    }

    /*主初始化*/
    public void init() {
        Gdx.app.debug(className, "主初始化");
    }

    /*处置*/
    @Override
    public void dispose() {
        saveConfig();
    }


    /*
     * 私有功能方法
     * */

    /*加载Json文件*/
    private void loadConfig(JsonValue json) {
        loadConfig("vanilla:", json.child); // 自动递归加载子节点，命名空间为原版
    }

    /*地洞递归拼接节点名，加载配置文件*/
    private void loadConfig(String id, JsonValue json){
        if(json.has("path")){
            idMap.put(id + json.name(), createResourceDescriptor(json));
            if(json.next != null) loadConfig(id , json.next);
        }
        else loadConfig(id + json.name() + ".", json.child);
        if(json.next() != null) loadConfig(id , json.next);
    }

    /*根据JsonValue中的type字段创建对应的ResourceDescriptor*/
    private static ResourceDescriptor createResourceDescriptor(JsonValue json){
        return switch (json.getString("type")) {
            case "json" -> new JsonDes(json.getString("path"));
            case "properties" -> new PropertiesDes(json.getString("path"));
            case "skin" -> new SkinDes(json.getString("path"));
            case "texture" -> new TextureDes(json.getString("path"));
            case "font" -> new FontDes(json.getString("path"));
            default -> null;
        };
    }

    /*保存配置文件*/
    private void saveConfig() {
        /*FileHandle config = resourceModule.getResourceManager().getFileHandle("config/config.properties");

        Properties properties = resourceModule.getResourceManager().get("config/config.properties", Properties.class);

        StringBuilder content = new StringBuilder();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            content.append(key).append("=").append(value).append("\n");
        }
        config.writeString(content.toString(), false);*/
    }

}
