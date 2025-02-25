package cn.tldream.ff.module.core.config;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.module.core.resource.ResourceManager;
import cn.tldream.ff.module.core.resource.ResourceModule;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

import java.util.Properties;

/**
 * 配置管理类
 * 依赖模块：资源管理器
 * */
public class ConfigManager implements Disposable {
    private final ResourceModule resourceModule;

    public ConfigManager(ResourceModule resourceModule) {
        this.resourceModule = resourceModule;
    }

    public void init() {
        resourceModule.getResourceManager().load("config/config.properties", Properties.class);
        resourceModule.getResourceManager().finishLoading();
        Properties config = resourceModule.getResourceManager().get("config/config.properties", Properties.class);
        System.out.println(config);
        System.out.println(config.getProperty("test"));
    }

    public void saveConfig() {
//        FileHandle config = resourceModule.getResourceManager().getFile("config/config.properties");

//        config.writeString("hello world",false);
    }

    @Override
    public void dispose() {
        saveConfig();
        resourceModule.getResourceManager().unload("config/config.properties");
    }
}
