package cn.tldream.ff.module.core.screen;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.module.core.config.ConfigKey;
import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.screen.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Map;


/**
 * 屏幕管理类
 * 预计未来依赖ui管理模块等
 */

public class ScreenManager implements Disposable {
    private final String className = "屏幕管理模器";
    private final FightGame game;     // 游戏实例
    private ConfigModule config;
    private final Map<Class<?>, BaseScreen> screens = new HashMap<>();  // 屏幕列表
    private Class<? extends BaseScreen> pendingScreen;  // 待切换的屏幕
    private OrthographicCamera camera;
    private Viewport viewport;

    /*构造函数*/
    public ScreenManager(FightGame game) {
        this.game = game;
    }

    /*依赖注入*/
    public void receiveDependency() {
        this.config = ConfigModule.getInstance();
    }

    /*预初始化*/
    public void preInit() {
        Gdx.app.debug(className, "预初始化");
        initViewport();
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

    /*获取视口*/
    public Viewport getViewport() {
        return viewport;
    }

    /*更新视口*/
    public void updateViewport(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }

    /*私有方法*/
    private void initViewport() {
        int width = config.getConfig(ConfigKey.WINDOW_WIDTH);
        int height = config.getConfig(ConfigKey.WINDOW_HEIGHT);

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(width, height, camera);
    }
}
