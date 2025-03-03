package cn.tldream.ff.module.core.screen;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.resource.ResourceManager;
import cn.tldream.ff.module.core.resource.ResourceModule;
import cn.tldream.ff.screen.BaseScreen;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * 屏幕管理模块
 *
 * */
public class ScreenModule implements GameModule {
    private final String className = "屏幕管理模块";
    private final ScreenManager screenManager;
    private boolean isInitialized = false;

    public ScreenModule(FightGame game) {
        screenManager = new ScreenManager(game);
    }

    /*暴露服务接口*/
    public ScreenManager getScreenManager() {
        return screenManager;
    }

    /*切换屏幕*/
    public <T extends BaseScreen> void switchTo(Class<T> screenClass) {
        screenManager.switchTo(screenClass);
    }

    /*获取视口*/
    public Viewport getViewport() {
        return screenManager.getViewport();
    }

    /*更新视口*/
    public void updateViewport(int width, int height) {
        screenManager.updateViewport(width, height);
    }

    @Override
    public String[] getDependencies() { return new String[] {"resource","config" ,"style", "ui", "layout"}; } // 依赖模块


    /*依赖注入*/
    @Override
    public void receiveDependency() {
        screenManager.receiveDependency();
    }

    /*预初始化*/
    @Override
    public void preInit() {
        screenManager.preInit();
    }

    /*主初始化*/
    @Override
    public void init() {
    }

    /*后初始化*/
    @Override
    public void postInit() {
        screenManager.postInit();
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
