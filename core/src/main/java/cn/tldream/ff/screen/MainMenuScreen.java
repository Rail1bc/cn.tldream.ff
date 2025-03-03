package cn.tldream.ff.screen;

import cn.tldream.ff.FightGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen extends BaseScreen {

    public MainMenuScreen(FightGame game) {
        super(game);
    }

    @Override
    public void loadAssets() {
        // 创建UI
        createUI();

        // 标记资源已加载
        setAssetsLoaded(true);
    }

    private void createUI() {
        Gdx.input.setInputProcessor(stage);  // 将输入交给 stage 处理

        // 使用 Table 布局
        Table table = layOutModule.getTable("mainMenu");

        stage.addActor(table);  // 将表格添加到舞台中

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);  // 更新舞台逻辑
        stage.draw();      // 绘制舞台
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

    }

}
