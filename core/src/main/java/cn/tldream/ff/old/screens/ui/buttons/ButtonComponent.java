package cn.tldream.ff.old.screens.ui.buttons;

import cn.tldream.ff.old.managers.StyleManager;
import cn.tldream.ff.old.screens.ui.UIComponent;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ButtonComponent extends UIComponent {
    private String text;
    private Runnable onClick;
    private TextButton.TextButtonStyle style;
    private boolean acceptKeyboardFocus = true;

    public ButtonComponent(String text, Runnable onClick) {
        this.text = text;
        this.onClick = onClick;
    }

    @Override
    // 创建按钮
    public void createActor() {
        TextButton btn = new TextButton(text, style);
//        btn.addListener(new ClickListener() {
////            @Override
////            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
////                if (pointer == -1) { // 鼠标悬停
////                    btn.getStyle().fontColor = Color.BLUE;
////                }
////            }
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                onClick.run();
//            }
//        });

        actor = btn;
    }

    @Override
    // 添加点击事件
    protected void registerListeners() {
        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (onClick != null) onClick.run();
            }
        });
    }

    @Override
    // 应用样式
    public void applyStyle(Skin skin) {
        // 每次调用新建按钮样式
        style = new  TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
        // 设置字体
        style.font = StyleManager.getFont(36);
    }

    @Override
    // 释放资源
    public void dispose() {
        style = null;
    }


    @Override
    // 允许键盘焦点
    public boolean supportsKeyboardFocus() {
        return acceptKeyboardFocus;
    }
}
