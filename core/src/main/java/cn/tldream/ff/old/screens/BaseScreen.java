package cn.tldream.ff.old.screens;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.old.managers.UIManager;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BaseScreen implements Screen {
    protected final FightGame game; // 引用FightGame
    protected UIManager uiManager;  // UI管理器
    protected Stage stage;  // 场景
    private boolean assetsLoaded = false;   // 资源是否加载

    public BaseScreen(FightGame game) {
        this.game = game;
        stage = new Stage();
        uiManager = new UIManager(stage);
    }

    public abstract void loadAssets();

    public boolean isAssetsLoaded() {
        return assetsLoaded;
    }

    protected void setAssetsLoaded(boolean loaded) {
        this.assetsLoaded = loaded;
    }

    @Override
    public void show(){

    }
}
