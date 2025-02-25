package cn.tldream.ff;

import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.resource.ResourceModule;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Event;

/** {@link Game} implementation shared by all platforms. */
public class FightGame extends Game {
    private Engine engine;
    private ModuleManager modules;

    public FightGame(String assetsPath) {
        // 实例化模块管理器
        modules = new ModuleManager();
        // 注册模块
        modules.register("resource", new ResourceModule(assetsPath));

    }

    @Override
    public void create() {

        // 初始化模块
        modules.initialize();

    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {
    }

}
