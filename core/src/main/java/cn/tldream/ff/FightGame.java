package cn.tldream.ff;

import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.ResourceModule;
import cn.tldream.ff.module.core.screen.ScreenModule;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;

/** {@link Game} implementation shared by all platforms. */
public class FightGame extends Game {
    private Engine engine;
    private ModuleManager modules;
    private String assetsPath;

    public FightGame(String assetsPath) {
        this.assetsPath = assetsPath;
        // 实例化模块管理器
        modules = new ModuleManager();
        // 注册模块
        modules.register("resource", new ResourceModule(assetsPath))
            .register("config", ConfigModule.getInstance())
            .register("screen", new ScreenModule(this));
    }

    public ModuleManager getModuleManager() {
        return modules;
    }

    @Override
    public void create() {

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
    }


}
