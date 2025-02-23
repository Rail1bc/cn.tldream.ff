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

/** {@link Game} implementation shared by all platforms. */
public class FightGame extends Game {
    private SpriteBatch batch;  // 渲染器
    private ScreenManager screens;  // 屏幕管理器
    private Engine engine;  // ashley引擎

    @Override
    public void create() {
        // 初始化样式
        StyleManager.initialize(this);
        ScreenManager.initialize(this);

        // 初始化资源管理器
        screens = ScreenManager.getInstance();
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

    /*获取渲染器*/
    public SpriteBatch getBatch() {
        if (batch == null) {
            batch = new SpriteBatch();
        }
        return batch;
    }

}
