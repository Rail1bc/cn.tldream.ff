package cn.tldream.ff.screens.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ResponsiveLayout {
    public static void apply(Table table) {
        float btnWidth = Gdx.graphics.getWidth() * 0.3f;
        float btnHeight = Gdx.graphics.getHeight() * 0.08f;

        table.getCells().forEach(cell -> {
            cell.width(btnWidth).height(btnHeight);
            cell.padBottom(Gdx.graphics.getHeight() * 0.02f);
        });
    }
}
