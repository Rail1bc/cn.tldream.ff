package cn.tldream.ff;

import cn.tldream.ff.managers.ScreenManager;
import cn.tldream.ff.managers.StyleManager;
import cn.tldream.ff.managers.resource.ResourceManager;
import cn.tldream.ff.screens.LoadingScreen;
import cn.tldream.ff.screens.MainMenuScreen;
import cn.tldream.ff.screens.SplashScreen;
import cn.tldream.ff.game.systems.MovementSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.lwjgl.Sys;

/** {@link Game} implementation shared by all platforms. */
public class FightGame extends Game {
    private SpriteBatch batch;
    private ScreenManager screens;
    private Engine engine;

    @Override
    public void create() {
        // 初始化样式
        StyleManager.initialize(this);

        // 初始化资源管理器
        screens = getScreens();
        screens.register(SplashScreen.class, new SplashScreen(this));
        screens.register(MainMenuScreen.class, new MainMenuScreen(this));
        screens.register(LoadingScreen.class, new LoadingScreen(this));

        // 切换到开始动画界面
        screens.switchTo(SplashScreen.class);

        // 初始化引擎
        engine = new Engine();
        engine.addSystem(new MovementSystem());

    }

    @Override
    public void render() {
        super.render();
        engine.update(Gdx.graphics.getDeltaTime());

    }
    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        if (screens != null) {
            screens.dispose();
        }
        StyleManager.dispose();
        ResourceManager.getInstance().dispose();
    }

    public SpriteBatch getBatch() {
        if (batch == null) {
            batch = new SpriteBatch();
        }
        return batch;
    }

    public ScreenManager getScreens() {
        if (screens == null) {
            screens = ScreenManager.initialize(this);
        }
        return screens;
    }

}
