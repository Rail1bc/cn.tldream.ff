package cn.tldream.ff.module.core.screen;

import cn.tldream.ff.module.core.config.ConfigKey;
import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.ResourceModule;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class StyleManager {
    private final String className = "样式管理器";
    private ResourceModule resourceModule; // 资源管理模块实例
    private ConfigModule configModule; // 配置管理模块实例
    private Skin skin;

    /*
     * 生命周期方法
     * 由样式管理模块调用
     * */

    /*构造函数*/
    public StyleManager(){
        Gdx.app.debug(className, "实例化");
    }

    /*依赖注入*/
    public void receiveDependency(ResourceModule resourceModule) {
        Gdx.app.debug(className, "依赖注入");
        this.resourceModule = resourceModule;
        this.configModule = ConfigModule.getInstance();
    }

    /*预初始化*/
    public void preInit(){
        Gdx.app.debug(className, "预初始化");
        skin = new Skin();

        int fontSize = configModule.getConfig(ConfigKey.WINDOW_WIDTH);
        resourceModule.setParameterFillName("vanilla:font.ttf.cn",fontSize/40); // 设置字体、大小，同步加载

        skin.add("cn",resourceModule.get("vanilla:font.ttf.cn"));
        skin.addRegions(resourceModule.loadAndGet("vanilla:atlas.uiskin.default"));
        skin.load(resourceModule.getFileHandle("vanilla:skin.uiskin.default"));
    }

    /*主初始化*/
    public void init() {
        Gdx.app.debug(className, "主初始化");
    }


    /*
    * 服务方法
    * */

    public Skin getSkin(){
        return skin;
    }

}
