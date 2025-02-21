package cn.tldream.ff.screens;


import cn.tldream.ff.FightGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

public class LoadingScreen extends BaseScreen {
    private float baseSize = 50f; // 基础图形尺寸
    private float currentScale = 0.5f; // 初始缩放
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private StringBuilder progressText;

    public LoadingScreen(FightGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont(); // 使用默认字体
        font.getData().setScale(2f);
        progressText = new StringBuilder(5);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float progress = resourceManager.getProgress();

        // 动态缩放计算
        currentScale = 0.5f + progress * 1.5f; // 0.5 ~ 2.0

        // 更新进度文本
//        progressText.clear();
        progressText.append((int)(progress * 100)).append("%");

        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);

        // 绘制动态图形
        drawLogo(progress);

        // 绘制进度文本
        stage.getBatch().begin();
        font.setColor(0.8f, 0.8f, 0.8f, 1f);
        font.draw(stage.getBatch(), progressText,
            Gdx.graphics.getWidth()/2f,
            Gdx.graphics.getHeight()/4f,
            0,
            Align.center,
            false);
        stage.getBatch().end();

        if (resourceManager.update()) {
            game.getScreens().updateLoading();
        }


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

    private void drawLogo(float progress) {
        float centerX = Gdx.graphics.getWidth()/2f;
        float centerY = Gdx.graphics.getHeight()/2f;
        float size = baseSize * currentScale;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // 绘制第一部分（矩形）
        shapeRenderer.setColor(0.2f + progress*0.8f, 0.4f, 0.6f, 1f);
        shapeRenderer.rect(
            centerX - size/2,
            centerY - size/2 + size*0.2f,
            size,
            size*0.8f
        );

        // 绘制第二部分（三角形）
        shapeRenderer.setColor(0.6f, 0.4f, 0.2f + progress*0.8f, 1f);
        shapeRenderer.triangle(
            centerX - size*0.4f, centerY + size*0.3f,
            centerX + size*0.4f, centerY + size*0.3f,
            centerX, centerY - size*0.5f
        );

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }

    @Override
    public void loadAssets() {
        setAssetsLoaded(true); // 无需额外资源
    }

}
