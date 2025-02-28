package cn.tldream.ff.module.core.screen;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.module.core.resource.ResourceManager;
import cn.tldream.ff.module.core.resource.ResourceModule;
import cn.tldream.ff.screen.*;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;


/**
 * 屏幕管理类
 * 预计未来依赖ui管理模块等
 */

public class ScreenManager implements Disposable {
    private final FightGame game;     // 游戏实例
    private final Map<Class<?>, BaseScreen> screens = new HashMap<>();  // 屏幕列表
    private Class<? extends BaseScreen> pendingScreen;  // 待切换的屏幕

    /*构造函数*/
    public ScreenManager(FightGame game) {
        this.game = game;
    }

    /*初始化引用*/
    public void postInit(){
        register(SplashScreen.class, new SplashScreen(game));
        register(LoadingScreen.class, new LoadingScreen(game));
        register(GameScreen.class, new GameScreen(game));
        register(MainMenuScreen.class, new MainMenuScreen(game));
        switchTo(SplashScreen.class);
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

    /*注册屏幕*/
    public <T extends BaseScreen> void register(Class<T> screenClass, T screen) {
        screens.put(screenClass, screen);
    }
}
