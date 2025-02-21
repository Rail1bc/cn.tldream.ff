package cn.tldream.ff.managers;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.screens.ui.StatefulComponent;
import cn.tldream.ff.screens.ui.UIComponent;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class UIManager {
    private final Array<UIComponent> components = new Array<>(); // UI组件
    private final Stage stage;  // 舞台

    public UIManager(Stage stage) {
        this.stage = stage;
    }

    // 注册组件
    public void registerComponent(UIComponent component) {
        // 构建组件
        Actor actor = component.
            build();

        // 将组件添加到管理器
        components.add(component);
    }

    public void addActor(Actor actor) {
       stage.addActor(actor);
    }

    public void updateLayout() {
        // 实现自动布局调整
        components.sort((a,b) -> Float.compare(a.getZIndex(), b.getZIndex()));
    }

    public void disposeAll() {
        components.forEach(UIComponent::dispose);
        components.clear();
    }

    public void onScreenResume() {
        components.forEach(c -> {
            if(c instanceof StatefulComponent) {
                ((StatefulComponent)c).onResume();
            }
        });
    }
}
