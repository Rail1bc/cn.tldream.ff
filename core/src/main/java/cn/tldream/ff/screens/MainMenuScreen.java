package cn.tldream.ff.screens;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.screens.ui.ComponentFactory;
import cn.tldream.ff.screens.ui.ResponsiveLayout;
import cn.tldream.ff.screens.ui.UIComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

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
        Table table = new Table();
        table.setFillParent(true);  // 铺满整个屏幕

        // 创建按钮
        addButton(table, "开始游戏", ()->game.getScreens().switchTo(GameScreen.class));
        addButton(table, "设置", ()->System.out.println("设置按钮"));
        addButton(table, "退出", Gdx.app::exit);

        // 应用响应式布局
        ResponsiveLayout.apply(table);
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

    private void addButton(Table table, String text, Runnable action) {
        // 通过工厂创建按钮
        UIComponent btn = ComponentFactory.createButton(text, action);

        // 注册按钮到UI管理器
//        uiManager.registerComponent(btn);

        // 将组件添加到表格中
        table.add(btn.build());//            .width(calculateWidth(BUTTON_WIDTH_RATIO))
//            .height(calculateHeight(BUTTON_HEIGHT_RATIO))
//            .padBottom(calculatePadding(0.02f));
//            .width(200)
//            .height(60)
//            .padBottom(10);


        // 添加下一行
        table.row();
    }
}
