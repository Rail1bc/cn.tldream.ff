package cn.tldream.ff.module.core.screen;

import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.ResourceModule;
import com.badlogic.gdx.Gdx;

public class UIModule implements GameModule {
    private final String className = "UI管理模块";
    private final UIManager uiManager; // UI管理器实例
    private StyleModule styleModule;

    /*
     * 生命周期方法
     * 由模块管理器调用
     * */

    /*构造函数*/
    public UIModule() {
        Gdx.app.debug(className, "实例化");
        uiManager = new UIManager();
    }

    /*获取依赖*/
    @Override
    public String[] getDependencies() {
        return new String[]{"style"}; // 依赖样式管理模块
    }

    /*依赖注入*/
    @Override
    public void receiveDependency() {
        Gdx.app.debug(className, "依赖注入");
        styleModule = ModuleManager.getModule("style", StyleModule.class);
        uiManager.receiveDependency(styleModule);
    }

    /*预初始化*/
    @Override
    public void preInit() {
        Gdx.app.debug(className, "预初始化");
        uiManager.preInit();
    }

    /*主初始化*/
    @Override
    public void init() {
        Gdx.app.debug(className, "主初始化");

    }

    @Override
    public void dispose() {

    }
}
