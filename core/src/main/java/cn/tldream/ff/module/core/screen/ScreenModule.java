package cn.tldream.ff.module.core.screen;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.resource.ResourceManager;
import cn.tldream.ff.module.core.resource.ResourceModule;


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

    @Override
    public int getInitPriority() { return 90; } // 次优先

    @Override
    public String[] getDependencies() { return new String[] {"resource"}; } // 依赖模块


    /*依赖注入*/
    @Override
    public void receiveDependency() {
        this.resourceModule = ModuleManager.getModule("resource", ResourceModule.class);
    }

    /*初始化*/
    @Override
    public void init() {
        screenManager.initialize();
        isInitialized = true;
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

}
