package cn.tldream.ff.module.core.screen;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.module.GameModule;
import cn.tldream.ff.module.core.resource.ResourceManager;
import cn.tldream.ff.module.core.resource.ResourceModule;

public class ScreenModule implements GameModule {
    private final ScreenManager screenManager;
    private ResourceModule resourceModule;

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
    public void receiveDependency(String name, GameModule module) {
        if ("resource".equals(name)) {
            this.resourceModule = (ResourceModule) module;
        }
    }

    @Override
    public void init() {
        screenManager.initialize();
    }

    @Override
    public void dispose() {

    }
}
