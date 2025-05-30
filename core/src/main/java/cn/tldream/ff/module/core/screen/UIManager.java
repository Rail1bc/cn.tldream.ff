package cn.tldream.ff.module.core.screen;

import cn.tldream.ff.module.core.ModuleManager;
import cn.tldream.ff.screen.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Map;

public class UIManager {
    private final String className = "UI管理器";
    private StyleModule styleModule; // 样式管理模块实例
    private final Map<String, Actor> uiMap;
    private TextButton.TextButtonStyle textButtonStyle;

    /*
     * 生命周期方法
     * 由样式管理模块调用
     * */

    /*构造函数*/
    public UIManager(Map<String, Actor> uiMap){
        Gdx.app.debug(className, "实例化");
        this.uiMap = uiMap;
    }

    /*依赖注入*/
    public void receiveDependency(StyleModule styleModule) {
        Gdx.app.debug(className, "依赖注入");
        this.styleModule = styleModule;
    }

    /*预初始化*/
    public void preInit(){
        Gdx.app.debug(className, "预初始化");
        styleInit();
        createUI();
    }

    /*主初始化*/
    public void init() {
        Gdx.app.debug(className, "主初始化");
    }

    /*后初始化*/
    public void postInit() {
        Gdx.app.debug(className, "后初始化");
    }


    /*
    * 私有方法
    * */

    /*初始化样式*/
    private void styleInit(){
        textButtonStyle = styleModule.getStyle(TextButton.TextButtonStyle.class);
//        textButtonStyle.font = styleModule.getFont();
    }

    private void createUI(){
        createButtonUI("btn_start", "开始游戏", ()-> ModuleManager.getModule("screen",ScreenModule.class).switchTo(GameScreen.class));
        createButtonUI("btn_setting", "设置", ()->System.out.println("设置按钮"));
        createButtonUI("btn_exit", "退出游戏", ()->Gdx.app.exit());
//        createLabelUI("label_title", "FG游戏");
    }

    private void createButtonUI(String id, String text, Runnable action){
        TextButton btn = new TextButton(text, textButtonStyle);
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (action != null) action.run();
            }
        });

        uiMap.put(id, btn);
    }

    private void createLabelUI(String id, String text){
        Label label = new Label(text, styleModule.getSkin());
        uiMap.put(id, label);
    }
}
