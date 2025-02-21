package cn.tldream.ff.screens;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.managers.UIManager;
import cn.tldream.ff.managers.resource.ResourceManager;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BaseScreen implements Screen {
    protected final FightGame game;
    protected final ResourceManager resourceManager;
    protected UIManager uiManager;
    protected Stage stage;
    private boolean assetsLoaded = false;

    public BaseScreen(FightGame game) {
        this.game = game;
        this.resourceManager = game.getResources();
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
