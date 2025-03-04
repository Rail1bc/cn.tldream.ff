package cn.tldream.ff.screen;

import cn.tldream.ff.FightGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;

public class MainMenuScreen extends BaseScreen {
    private final String className = "主菜单界面";

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
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        Table spacerTable = new Table();
        rootTable.add(spacerTable).width(Value.percentWidth(0.625f,rootTable));
        rootTable.add(layOutModule.getTable("mainMenu"))
            .width(Value.percentWidth(0.25f,rootTable))
            .height(Value.percentHeight(1f,rootTable))
        ;

        stage.addActor(rootTable);  // 将表格添加到舞台中

    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getViewport().apply(); //应用视口
        stage.act(delta);  // 更新舞台逻辑
        stage.draw();      // 绘制舞台
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
