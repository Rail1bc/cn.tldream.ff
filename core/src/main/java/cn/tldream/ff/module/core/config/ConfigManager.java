package cn.tldream.ff.module.core.config;

import cn.tldream.ff.module.core.resource.ResourceModule;
import cn.tldream.ff.module.core.resource.descriptor.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 配置管理器
 * 依赖模块：资源管理器
 * */
public class ConfigManager implements Disposable {
    private final String className = "配置管理器";
    public final Map<String, ResourceDescriptor> idMap = new ConcurrentHashMap<>(); // id与资源描述符的映射
    private final ResourceModule resourceModule;

    /*构造函数*/
    public ConfigManager(ResourceModule resourceModule) {
        Gdx.app.log(className, "实例化");
        idMap.put("vanilla:core", new JsonDes("core.json")); // 配置文件，全局唯一硬编码的相对路径
        this.resourceModule = resourceModule; // 依赖注入
    }

    /*读取配置文件*/
    public void init() {
        Gdx.app.log(className, "初始化");
    }

    /*保存配置文件*/
    public void saveConfig() {
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

    /*获取路径*/
    public ResourceDescriptor getRes(String id){
        if(idMap.containsKey(id)) return idMap.get(id);
        return null;
    }

    /*加载Json文件*/
    public void loadConfig(JsonValue json) {
        loadConfig("vanilla:", json.child);
    }

    @Override
    public void dispose() {
        saveConfig();
    }

    public void loadConfig(String id, JsonValue json){
        if(json.has("path")){
            idMap.put(id + json.name(), createResourceDescriptor(json));
            if(json.next != null) loadConfig(id , json.next);
        }
        else loadConfig(id + json.name() + ".", json.child);

        if(json.next() != null) loadConfig(id , json.next);
    }

    /*根据JsonValue中的type字段创建对应的ResourceDescriptor*/
    public static ResourceDescriptor createResourceDescriptor(JsonValue json){
        return switch (json.getString("type")) {
            case "json" -> new JsonDes(json.getString("path"));
            case "properties" -> new PropertiesDes(json.getString("path"));
            case "skin" -> new SkinDes(json.getString("path"));
            case "texture" -> new TextureDes(json.getString("path"));
            case "font" -> new FontDes(json.getString("path"));
            default -> null;
        };
    }
}
