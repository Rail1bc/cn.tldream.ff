package cn.tldream.ff.module.core.screen;

import com.badlogic.gdx.Gdx;

public class UIManager {
    private final String className = "UI管理器";
    private StyleModule styleModule; // 样式管理模块实例

    /*
     * 生命周期方法
     * 由样式管理模块调用
     * */

    /*构造函数*/
    public UIManager(){
        Gdx.app.debug(className, "实例化");
    }

    /*依赖注入*/
    public void receiveDependency(StyleModule styleModule) {
        Gdx.app.debug(className, "依赖注入");
        this.styleModule = styleModule;
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
