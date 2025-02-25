package cn.tldream.ff.old.screens.ui;

import cn.tldream.ff.old.screens.ui.buttons.ButtonComponent;

public class ComponentFactory {

    public static UIComponent createButton(String text, Runnable action) {
        return new ButtonComponent(text, action);
    }
}
