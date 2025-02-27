package cn.tldream.ff.screen;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.resource.ResourceModule;
import cn.tldream.ff.module.core.screen.ScreenModule;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BaseScreen implements Screen {
    protected FightGame game;
    protected ScreenModule screenModule;
    protected ResourceModule resourceModule;
    protected Stage stage;  // 场景
    private boolean assetsLoaded = false;   // 资源是否加载

    public BaseScreen(FightGame game) {
        stage = new Stage();
        this.game = game;
        screenModule = ModuleManager.getModule("screen", ScreenModule.class);
        resourceModule = ModuleManager.getModule("resource", ResourceModule.class);
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
