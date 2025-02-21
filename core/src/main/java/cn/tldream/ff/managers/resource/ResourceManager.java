package cn.tldream.ff.managers.resource;

import cn.tldream.ff.FightGame;
import cn.tldream.ff.enums.ResourceKind;
import cn.tldream.ff.enums.ResourceType;
import cn.tldream.ff.managers.resource.type.FontConfig;
import cn.tldream.ff.managers.resource.type.ResourcesType;
import cn.tldream.ff.managers.resource.type.TextureConfig;
import cn.tldream.ff.managers.resource.type.SkinConfig;
import cn.tldream.ff.managers.resource.config.ConfigsType;
import cn.tldream.ff.managers.resource.item.ResourcesItem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager extends AssetManager{
    private final static ResourceManager instance = new ResourceManager();  // 单例
    private final Map<String, ResourcesType> resTypeMap = new HashMap<>();  // 资源类型
    private final Map<String, ResourcesItem> resItemMap = new HashMap<>();  // 资源项
    private final Map<String, ConfigsType> cfgTypeMap = new HashMap<>();    // 配置类型

    /**
     * 单例模式
     */

    public static ResourceManager getInstance() {
        return instance;
    }

    private ResourceManager() {
        initialize();
    }

    /**
     * 初始化资源管理器
     */
    private void initialize() {
        loadConfiguration();
        loadAssets();
        freeTypeFontLoader();
    }

    /**
    * 加载资源配置
    */
    private void loadConfiguration() {
        resTypeMap.put("texture", new TextureConfig().init());
        resTypeMap.put("skin", new SkinConfig().init());
        resTypeMap.put("font", new FontConfig().init());
    }

    /**
     * 强制加载核心资源
     */
    private void loadAssets() {

        load(resTypeMap.get(ResourceType.SKIN.getValue()).getPath(ResourceKind.uiskin,"default"),Skin.class);
        load(resTypeMap.get(ResourceType.TEXTURE.getValue()).getPath(ResourceKind.logo,"engine_logo"),Texture.class);
        load(resTypeMap.get(ResourceType.TEXTURE.getValue()).getPath(ResourceKind.logo,"tld_logo_p1"),Texture.class);
        load(resTypeMap.get(ResourceType.TEXTURE.getValue()).getPath(ResourceKind.logo,"tld_logo_p2"),Texture.class);
        finishLoading();
    }

    /**
     * 设置FreeType 字体加载器
     */
    private void freeTypeFontLoader() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
    }

}
