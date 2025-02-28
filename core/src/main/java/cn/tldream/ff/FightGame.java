package cn.tldream.ff;

import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.ResourceModule;
import cn.tldream.ff.module.core.screen.ScreenModule;
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

    public FightGame(String assetsPath) {
        this.assetsPath = assetsPath; // 设置资源路径

        this.modules = ModuleManager.getInstance(); // 获取模块管理器实例
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG); // 设置日志级别为调试级别
        // 注册模块
        modules
            .register("config", ConfigModule.getInstance())
            .register("resource", new ResourceModule(assetsPath))
            .register("screen", new ScreenModule(this));

        // 初始化模块
        modules.initialize();
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
}
