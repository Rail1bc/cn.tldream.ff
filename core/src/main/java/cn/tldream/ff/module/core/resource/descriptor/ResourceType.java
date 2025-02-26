package cn.tldream.ff.module.core.resource.descriptor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Properties;

public enum ResourceType {
    texture(Texture.class),
    skin(Skin.class),
    json( JsonValue.class),
    properties(Properties.class),
    font(BitmapFont.class);

    private final Class<?> val;

    ResourceType(Class<?> typeClass) {
        val = typeClass;
    }

    public Class<?> getVal() {
        return val;
    }



}
