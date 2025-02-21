package cn.tldream.ff.screens;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.enums.ScreenType;
import cn.tldream.ff.managers.resource.ResourceManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;

public class SplashScreen extends BaseScreen {
    private Texture engineLogo;
    private Texture studioLogoPart1;
    private Texture studioLogoPart2;
    private int currentPhase = 0;
    private float alpha = 0;
    private final float[] phaseTimes = {2f, 3f};

    public SplashScreen(FightGame game) {
        super(game);
    }


    @Override
    // 加载资源（实际路径需要根据你的资源位置调整）
    public void loadAssets() {
        engineLogo = resourceManager.get("logos/libgdx.png", Texture.class);
        studioLogoPart1 = resourceManager.get("logos/tld_p1.png", Texture.class);
        studioLogoPart2 = resourceManager.get("logos/tld_p2.png", Texture.class);
        resourceManager.finishLoading();
        setAssetsLoaded(true);
        game.getScreens().switchTo(SplashScreen.class);
    }

    @Override
    public void show() {
        super.show();
        // 获取所有Logo资源
//        engineLogo = systems.resources.getByType(ResourceType.TEXTURE, ResourceKind.logo, "engine_logo", Texture.class);
//        studioLogoPart1 = systems.resources.getByType(ResourceType.TEXTURE, ResourceKind.logo, "tld_logo_p1", Texture.class);
//        studioLogoPart2 = systems.resources.getByType(ResourceType.TEXTURE, ResourceKind.logo, "tld_logo_p2", Texture.class);


        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                skipToMenu();
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                skipToMenu();
                return true;
            }
        });

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                currentPhase++;
                alpha = 0; // 重置透明度用于淡入
                if(currentPhase >= phaseTimes.length) {
                    transitionToMainMenu();
                }
            }
        }, phaseTimes[0], phaseTimes[1]);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        alpha = Math.min(alpha + delta * 2, 1); // 加快淡入速度

        stage.getBatch().begin();
        stage.getBatch().setColor(1, 1, 1, alpha);

        switch(currentPhase) {
            case 0: // 显示引擎Logo
                drawCentered(engineLogo);
                break;

            case 1: // 显示组合工作室Logo
                float totalHeight = studioLogoPart1.getHeight() + studioLogoPart2.getHeight();
                float startY = (Gdx.graphics.getHeight() - totalHeight)/2f;

                // 绘制上半部分
                float x = (Gdx.graphics.getWidth() - studioLogoPart1.getWidth())/2f;
                stage.getBatch().draw(studioLogoPart1, x, startY + studioLogoPart2.getHeight());

                // 绘制下半部分
                stage.getBatch().draw(studioLogoPart2, x, startY);
                break;
        }

        stage.getBatch().setColor(1, 1, 1, 1);
        stage.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void drawCentered(Texture texture) {
        float x = (Gdx.graphics.getWidth() - texture.getWidth())/2f;
        float y = (Gdx.graphics.getHeight() - texture.getHeight())/2f;
        stage.getBatch().draw(texture, x, y);
    }

    private void transitionToMainMenu() {
        Timer.instance().clear();
        game.getScreens().switchTo(MainMenuScreen.class);
    }

    private void skipToMenu() {
        Timer.instance().clear();    // 取消所有定时任务
        transitionToMainMenu();      // 立即跳转
    }
    // 其他需要实现的空方法...
}
