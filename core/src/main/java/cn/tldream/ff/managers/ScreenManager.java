package cn.tldream.ff.managers;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.screens.BaseScreen;
import cn.tldream.ff.screens.LoadingScreen;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager implements Disposable {
    private static ScreenManager instance;
    private final FightGame game;
    private final Map<Class<?>, BaseScreen> screens;
    private Class<? extends BaseScreen> pendingScreen;  // 待切换的屏幕

    private ScreenManager(FightGame game) {
        this.game = game;
        this.screens = new HashMap<>();
    }

    public static ScreenManager initialize(FightGame game) {
        return instance = new ScreenManager(game);
    }

    public static ScreenManager getInstance() {
        return instance;
    }

    // 注册屏幕
    public <T extends BaseScreen> void register(Class<T> screenClass, T screen) {
        screens.put(screenClass, screen);
    }

    // 切换屏幕
    public <T extends BaseScreen> void switchTo(Class<T> screenClass) {
        BaseScreen screen = screens.get(screenClass);
        if (screen != null) {
            if (screen.isAssetsLoaded()) {
                game.setScreen(screen);
            } else {
                this.pendingScreen = screenClass;
                game.setScreen(screens.get(LoadingScreen.class));
                screen.loadAssets();
            }
        }
    }

    // 更新加载进度
    public void updateLoading() {
        if (pendingScreen != null) {
            BaseScreen screen = screens.get(pendingScreen);
            if (screen != null && screen.isAssetsLoaded()) {
                game.setScreen(screen);
                pendingScreen = null;
            }
        }
    }

    // 释放资源
    @Override
    public void dispose() {
        for (BaseScreen screen : screens.values()) {
            screen.dispose();
        }
        screens.clear();
    }
}
