package cn.tldream.ff.module.core.screen;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.module.GameModule;

public class ScreenModule implements GameModule {
    private final ScreenManager screenManager;

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

    @Override
    public void init() {
        screenManager.initialize();
    }

    @Override
    public void dispose() {

    }
}
