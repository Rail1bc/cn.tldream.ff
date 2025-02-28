package cn.tldream.ff.module.core.style;

import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.ResourceManager;
import cn.tldream.ff.module.core.resource.ResourceModule;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class StyleModule implements GameModule {
    private final String className = "样式管理器";
    private final StyleManager styleManager;
    private ResourceModule resourceModule; // 资源管理模块实例
    private ConfigModule configModule; // 配置管理模块实例

    /*
     * 生命周期方法
     * 由模块管理器调用
     * */

    /*构造函数*/
    public StyleModule() {
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
        styleManager.receiveDependency(ModuleManager.getModule("resource", ResourceModule.class));
    }

    /*预初始化*/
    @Override
    public void preInit() {
        styleManager.preInit();
    }

    @Override
    public void init() {

    }

    @Override
    public void dispose() {

    }

    public BitmapFont getFont() {
        return styleManager.getFont();
    }
}
