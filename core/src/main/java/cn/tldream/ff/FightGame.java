package cn.tldream.ff;

import cn.tldream.ff.old.managers.ModuleManager;
import cn.tldream.ff.old.module.ResourceModule;
import cn.tldream.ff.old.module.ScreenModule;
import cn.tldream.ff.old.screens.SplashScreen;
import cn.tldream.ff.old.game.systems.MovementSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link Game} implementation shared by all platforms. */
public class FightGame extends Game {
    private ModuleManager modules; // 模块管理器
    private boolean modulesInitialized = false; // 模块是否初始化
    private SpriteBatch batch;  // 渲染器
    private Engine engine;

    public FightGame(String assetsPath) {
        super();
        this.modules = new ModuleManager()
            .register("resource", new ResourceModule(assetsPath))
            .register("ui", new UIModule())
            .register("screen", new ScreenModule());
    }

    @Override
    public void create() {
        // 初始化所有模块
        modules.initialize();
        modulesInitialized = true;

        // 初始化引擎
        engine = new Engine();
        engine.addSystem(new MovementSystem());

        // 启动首个场景
        getScreenModule().switchTo(SplashScreen.class);



    }

    @Override
    public void render() {
        super.render();
        engine.update(Gdx.graphics.getDeltaTime());

    }

    @Override
    public void dispose() {
        modules.dispose();
    }

    /*获取渲染器*/
    public SpriteBatch getBatch() {
        if (batch == null) {
            batch = new SpriteBatch();
        }
        return batch;
    }

    // 提供模块快捷访问
    public ScreenModule getScreenModule() {
        return modules.getModule("screen", ScreenModule.class);
    }

}
