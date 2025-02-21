package cn.tldream.ff.managers;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.managers.resource.ResourceManager;
import cn.tldream.ff.screens.ui.UIComponent;
import cn.tldream.ff.screens.ui.buttons.ButtonComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class StyleManager {
    private static FightGame game; // 游戏实例
    private static Skin skin;   // 皮肤
    private static final String DEFAULT_SKIN = "ui/uiskin.json";    // 默认皮肤
    private static final ResourceManager resourceManager = ResourceManager.getInstance();   // 资源管理器

    // 初始化样式
    public static void initialize(FightGame game) {
        StyleManager.game = game;
        resourceManager.load(DEFAULT_SKIN, Skin.class);
        skin = resourceManager.get(DEFAULT_SKIN, Skin.class);
    }

    // 获取样式
    public static <T> T getStyle(String name, Class<T> styleClass) {
        return skin.get(name, styleClass);
    }

    // 获取皮肤
    public static Skin getSkin() {
        return skin;
    }

    public static BitmapFont getFont(int size){
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.incremental = true;
//        parameter.size = size;
//        // font size 12 pixels
////                generator.dispose();
//        BitmapFont bf = generator.generateFont(parameter);
//        bf.getData().markupEnabled = true;
//        return bf;


        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter.fontParameters.incremental = true;
        parameter.fontFileName = "fonts/simhei.ttf";
        parameter.fontParameters.size = size;

        // 加载字体
        resourceManager.load("fonts/simhei.ttf", BitmapFont.class , parameter);

        resourceManager.finishLoading();

        // 获取字体
        BitmapFont myBigFont = resourceManager.get("fonts/simhei.ttf", BitmapFont.class);
        // 设置字体支持Markup
        myBigFont.getData().markupEnabled = true;
        return myBigFont;
    }


    // 更新字体大小
    public static void updateScale(float scaleFactor) {
        skin.getFont("default-font").getData().setScale(scaleFactor);
    }

    // 应用样式
    public static void applyComponentStyle(UIComponent component) {
        if(component instanceof ButtonComponent) {
            ButtonComponent btn = (ButtonComponent)component;
            btn.applyStyle(getStyle("menuButton", Skin.class));
        }
    }

    public static void dispose(){
        skin.dispose();
    }
}
