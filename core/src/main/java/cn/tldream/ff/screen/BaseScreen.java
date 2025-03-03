package cn.tldream.ff.screen;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.module.core.resource.ResourceModule;
import cn.tldream.ff.module.core.screen.LayOutModule;
import cn.tldream.ff.module.core.screen.ScreenModule;
import cn.tldream.ff.module.core.screen.StyleModule;
import cn.tldream.ff.module.core.screen.UIModule;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public abstract class BaseScreen implements Screen {
    protected FightGame game;
    protected ScreenModule screenModule;
    protected ResourceModule resourceModule;
    protected StyleModule styleModule;
    protected UIModule uiModule;
    protected LayOutModule layOutModule;
    protected Stage stage;  // 场景
    private boolean assetsLoaded = false;   // 资源是否加载

    public BaseScreen(FightGame game) {
        this.game = game;
        screenModule = ModuleManager.getModule("screen", ScreenModule.class);
        resourceModule = ModuleManager.getModule("resource", ResourceModule.class);
        styleModule = ModuleManager.getModule("style", StyleModule.class);
        uiModule = ModuleManager.getModule("ui", UIModule.class);
        layOutModule = ModuleManager.getModule("layout", LayOutModule.class);
        stage = new Stage(screenModule.getViewport());
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

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
}
