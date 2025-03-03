package cn.tldream.ff.module.core.screen;

import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.resource.ResourceModule;
import com.badlogic.gdx.Gdx;

public class LayOutModule implements GameModule {
    private final String className = "布局管理模块";
    private UIModule uiModule; // UI模块
    private LayOutManager layOutManager; // 布局管理器

    /*
     * 生命周期方法
     * 由样式管理模块调用
     * */

    /*构造函数*/
    public LayOutModule(){
        Gdx.app.debug(className, "实例化");
    }

    /*获取依赖*/
    @Override
    public String[] getDependencies() {
        return new String[]{"ui"}; // 依赖配置管理模块、资源管理模块
    }

    /*依赖注入*/
    @Override
    public void receiveDependency() {
        Gdx.app.debug(className, "依赖注入");
        layOutManager.receiveDependency(ModuleManager.getModule("ui", UIModule.class));
    }

    /*预初始化*/
    public void preInit(){
        Gdx.app.debug(className, "预初始化");
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
