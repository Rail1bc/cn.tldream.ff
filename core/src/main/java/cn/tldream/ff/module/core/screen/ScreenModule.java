package cn.tldream.ff.module.core.screen;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.resource.ResourceManager;
import cn.tldream.ff.module.core.resource.ResourceModule;
import cn.tldream.ff.screen.BaseScreen;


/**
 * 屏幕管理模块
 *
 * */
public class ScreenModule implements GameModule {
    private final ScreenManager screenManager;
    private ResourceModule resourceModule;
    private boolean isInitialized = false;

    public ScreenModule(FightGame game) {
        screenManager = new ScreenManager(game);
    }

    /*暴露服务接口*/
    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public <T extends BaseScreen> void switchTo(Class<T> screenClass) {
        screenManager.switchTo(screenClass);
    }

    @Override
    public String[] getDependencies() { return new String[] {"resource"}; } // 依赖模块


    /*依赖注入*/
    @Override
    public void receiveDependency() {
        this.resourceModule = ModuleManager.getModule("resource", ResourceModule.class);
    }

    /*主初始化*/
    @Override
    public void init() {
        isInitialized = true;
    }

    /*后初始化*/
    @Override
    public void postInit() {
        screenManager.postInit();
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

}
