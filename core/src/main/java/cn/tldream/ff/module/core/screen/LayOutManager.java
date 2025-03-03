package cn.tldream.ff.module.core.screen;


import cn.tldream.ff.module.core.config.ConfigKey;
import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.ResourceModule;
import com.badlogic.gdx.Gdx;

public class LayOutManager {
    private final String className = "布局管理器";
    private UIModule uiModule;

    /*
     * 生命周期方法
     * 由样式管理模块调用
     * */

    /*构造函数*/
    public LayOutManager(){
        Gdx.app.debug(className, "实例化");
    }

    /*依赖注入*/
    public void receiveDependency(UIModule uiModule) {
        Gdx.app.debug(className, "依赖注入");
        this.uiModule = uiModule;
    }

    /*预初始化*/
    public void preInit(){
        Gdx.app.debug(className, "预初始化");
    }

    /*主初始化*/
    public void init() {
        Gdx.app.debug(className, "主初始化");
    }
}
