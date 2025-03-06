package cn.tldream.ff.module.core.screen;

import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.ResourceModule;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class StyleModule implements GameModule {
    private final String className = "样式管理模块";
    private final StyleManager styleManager; // 样式管理器
    private ResourceModule resourceModule; // 资源管理模块实例
    private ConfigModule configModule; // 配置管理模块实例

    /*
     * 生命周期方法
     * 由模块管理器调用
     * */

    /*构造函数*/
    public StyleModule() {
        Gdx.app.debug(className, "实例化");
        styleManager = new StyleManager();
    }

    /*获取依赖*/
    @Override
    public String[] getDependencies() {
        return new String[]{"config", "resource"}; // 依赖配置管理模块、资源管理模块
    }

    /*依赖注入*/
    @Override
    public void receiveDependency() {
        Gdx.app.debug(className, "依赖注入");
        styleManager.receiveDependency(ModuleManager.getModule("resource", ResourceModule.class));
    }

    /*预初始化*/
    @Override
    public void preInit() {
        Gdx.app.debug(className, "预初始化");
        styleManager.preInit();
    }

    /*主初始化*/
    @Override
    public void init() {
        Gdx.app.debug(className, "主初始化");

    }

    @Override
    public void dispose() {

    }

    /*
     * 服务方法
     * */

    public Skin getSkin() {
        return styleManager.getSkin();
    }


    /*获取样式*/
    public <T> T getStyle(Class<T> type){
        return getSkin().get(type);
    }
}
