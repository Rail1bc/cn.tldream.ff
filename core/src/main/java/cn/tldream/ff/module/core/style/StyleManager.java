package cn.tldream.ff.module.core.style;

import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.ResourceModule;
import com.badlogic.gdx.Gdx;

public class StyleManager {
    private final String className = "样式管理器";
    private ResourceModule resourceModule; // 资源管理模块实例
    private ConfigModule configModule; // 配置管理模块实例

    /*
     * 生命周期方法
     * 由样式管理模块调用
     * */

    /*构造函数*/
    public StyleManager(){

    }

    /*依赖注入*/
    public void receiveDependency(ResourceModule resourceModule) {
        this.resourceModule = resourceModule;
        this.configModule = ConfigModule.getInstance();
    }

    /*预初始化*/
    public void preInit(){
        Gdx.app.debug(className, "预初始化");
        resourceModule.setParameter("vanilla:font.ttf.cn");
    }


    /*主初始化*/
    public void init() {
        Gdx.app.debug(className, "主初始化");
    }
}
