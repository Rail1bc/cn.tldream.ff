package cn.tldream.ff.module.core.screen;

import cn.tldream.ff.FightGame;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager implements Disposable {
    private final static ScreenManager instance = new ScreenManager();  // 单例
    private static FightGame game;     // 游戏实例
    private final Map<Class<?>, BaseScreen> screens;    // 屏幕列表
    private Class<? extends BaseScreen> pendingScreen;  // 待切换的屏幕

    /*单例模式*/
    private ScreenManager() {
        this.screens = new HashMap<>();
    }

    public static void initialize(FightGame game) {
        ScreenManager.game = game;
    }

    public static ScreenManager getInstance() {
        return instance;
    }

    /*注册屏幕*/
    public <T extends BaseScreen> void register(Class<T> screenClass, T screen) {
        screens.put(screenClass, screen);
    }

    /*切换屏幕*/
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

    /*更新加载进度
    如果有待切换的屏幕，则更新加载进度
    如果加载完成，则切换屏幕*/
    public void updateLoading() {
        if (pendingScreen != null) {
            BaseScreen screen = screens.get(pendingScreen);
            if (screen != null && screen.isAssetsLoaded()) {
                game.setScreen(screen);
                pendingScreen = null;
            }
        }
    }

    /*释放资源*/
    @Override
    public void dispose() {
        for (BaseScreen screen : screens.values()) {
            screen.dispose();
        }
        screens.clear();
    }
}
