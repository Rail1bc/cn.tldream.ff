package cn.tldream.ff.screens.ui;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.managers.StyleManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class UIComponent {
    protected Actor actor;
    protected Skin skin;
    protected int zIndex;

    // 模板方法模式定义生命周期
    public final Actor build() {
        applyStyle(StyleManager.getSkin());
        createActor();
        registerListeners();
        return actor;
    }

    protected abstract void createActor();
    protected abstract void applyStyle(Skin skin);
    protected abstract void registerListeners();

    // 释放资源
    public void dispose() {
        actor.clearListeners();
        actor.remove();
    }

    // 处理窗口大小变化
    public void onWindowResize(int newWidth, int newHeight) {
        // 处理窗口大小变化
    }

    // 获取组件的键盘焦点支持状态
    public boolean supportsKeyboardFocus() {
        return false; // 默认不支持，按需重写
    }

    // 获取组件的Z索引
    public float getZIndex() {
        return zIndex;
    }
}
