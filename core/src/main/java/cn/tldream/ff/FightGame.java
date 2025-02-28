package cn.tldream.ff;

import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.config.ConfigKey;
import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.ResourceModule;
import cn.tldream.ff.module.core.screen.ScreenModule;
import cn.tldream.ff.module.core.style.StyleModule;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/** {@link Game} implementation shared by all platforms. */
/**
 * 主类
 * 启动必要参数：
 * assetsPath:资源目录路径
 * */
public class FightGame extends Game {
    private Engine engine; // ashley
    private final ModuleManager modules; // 模块管理器
    private final String assetsPath; //资源目录路径
    private ConfigModule config;

    public FightGame(String assetsPath) {
        this.assetsPath = assetsPath; // 设置资源路径

        this.modules = ModuleManager.getInstance(); // 获取模块管理器实例
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(3);

        // 注册模块
        modules
            .register("config", config = ConfigModule.getInstance())
            .register("resource", new ResourceModule(assetsPath))
            .register("style", new StyleModule())
            .register("screen", new ScreenModule(this))
        ;


        // 初始化模块
        modules.initialize();

        Gdx.app.setLogLevel(ConfigModule.getInstance().getConfig(ConfigKey.LOG_LEVEL)); // 设置日志级别为调试级别

        applyWindowConfig();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        modules.dispose();
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        config.setConfig(ConfigKey.WINDOW_WIDTH, width);
        config.setConfig(ConfigKey.WINDOW_HEIGHT, height);
        applyWindowConfig();
    }

    public void applyWindowConfig() {
        int width = config.getConfig(ConfigKey.WINDOW_WIDTH);
        int height = config.getConfig(ConfigKey.WINDOW_HEIGHT);
        boolean fullscreen = config.getConfig(ConfigKey.FULLSCREEN);

        if (fullscreen) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            Gdx.graphics.setWindowedMode(width, height);
        }
    }
}
