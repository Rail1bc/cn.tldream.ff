package cn.tldream.ff.screen;

import cn.tldream.ff.FightGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

public class SplashScreen extends BaseScreen {
    private Texture engineLogo;     // 引擎Logo
    private Texture studioLogoPart1;    // 组合工作室Logo
    private Texture studioLogoPart2;    // 组合工作室Logo
    private int currentPhase = 0;   // 当前阶段
    private float alpha = 0;    // 透明度
    private final float[] phaseTimes = {2f, 3f};    // 不同阶段显示时间

    public SplashScreen(FightGame game) {
        super(game);
    }

    /*获取核心资源*/
    @Override
    public void loadAssets() {
        engineLogo = resourceModule.get("vanilla:texture.logo.engine_logo");
        studioLogoPart1 = resourceModule.get("vanilla:texture.logo.tld_p1");
        studioLogoPart2 = resourceModule.get("vanilla:texture.logo.tld_p2");
        setAssetsLoaded(true);
        screenModule.getScreenManager().switchTo(SplashScreen.class);
    }

    @Override
    public void show() {

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

        alpha = Math.min(alpha + delta * 200, 1); // 加快淡入速度

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

    /*绘制居中的纹理*/
    private void drawCentered(Texture texture) {
        float x = (Gdx.graphics.getWidth() - texture.getWidth())/2f;
        float y = (Gdx.graphics.getHeight() - texture.getHeight())/2f;
        stage.getBatch().draw(texture, x, y);
    }

    /*切换到主界面菜单*/
    private void transitionToMainMenu() {
        Timer.instance().clear();
        screenModule.getScreenManager().switchTo(MainMenuScreen.class);
    }

    /*跳转到主界面菜单*/
    private void skipToMenu() {
        Timer.instance().clear();    // 取消所有定时任务
        transitionToMainMenu();      // 立即跳转
    }
}
