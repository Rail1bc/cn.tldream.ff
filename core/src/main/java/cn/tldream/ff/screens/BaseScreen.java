package cn.tldream.ff.screens;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.managers.UIManager;
import cn.tldream.ff.managers.resource.ResourceManager;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BaseScreen implements Screen {
    protected final FightGame game; // 引用FightGame
    protected final ResourceManager resourceManager;    // 资源管理器
    protected UIManager uiManager;  // UI管理器
    protected Stage stage;  // 场景
    private boolean assetsLoaded = false;   // 资源是否加载

    public BaseScreen(FightGame game) {
        this.game = game;
        this.resourceManager = ResourceManager.getInstance();
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
