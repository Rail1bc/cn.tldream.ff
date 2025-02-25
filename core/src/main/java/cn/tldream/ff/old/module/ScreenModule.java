package cn.tldream.ff.old.module;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.old.managers.ScreenManager;
import cn.tldream.ff.old.screens.BaseScreen;
import cn.tldream.ff.old.screens.LoadingScreen;
import cn.tldream.ff.old.screens.MainMenuScreen;
import cn.tldream.ff.old.screens.SplashScreen;

public class ScreenModule implements GameModule{
    private ScreenManager screens;
    private FightGame game;

    public <T extends BaseScreen> void switchTo(Class<T> screenClass){
        screens.switchTo(screenClass);
    }

    @Override
    public void preInit() {
    }

    @Override
    public void init() {
        screens.register(SplashScreen.class, new SplashScreen(game));
        screens.register(MainMenuScreen.class, new MainMenuScreen(game));
        screens.register(LoadingScreen.class, new LoadingScreen(game));
    }

    @Override
    public void postInit() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void dispose() {

    }
}
