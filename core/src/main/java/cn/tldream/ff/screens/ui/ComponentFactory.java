package cn.tldream.ff.screens.ui;

import cn.tldream.ff.screens.ui.buttons.ButtonComponent;

public class ComponentFactory {

    public static UIComponent createButton(String text, Runnable action) {
        return new ButtonComponent(text, action);
    }
}
