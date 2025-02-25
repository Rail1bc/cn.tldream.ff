package cn.tldream.ff.old.managers.resource;

import cn.tldream.ff.old.enums.ResourceKind;
import cn.tldream.ff.old.enums.ResourceType;
import cn.tldream.ff.old.managers.resource.type.FontConfig;
import cn.tldream.ff.old.managers.resource.type.ResourcesType;
import cn.tldream.ff.old.managers.resource.type.TextureConfig;
import cn.tldream.ff.old.managers.resource.type.SkinConfig;
import cn.tldream.ff.old.managers.resource.config.ConfigsType;
import cn.tldream.ff.old.managers.resource.item.ResourcesItem;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager extends AssetManager{
    private final String assetsPath;
    private final Map<String, ResourcesType> resTypeMap = new HashMap<>();  // 资源类型
    private final Map<String, ResourcesItem> resItemMap = new HashMap<>();  // 资源项
    private final Map<String, ConfigsType> cfgTypeMap = new HashMap<>();    // 配置类型

    public ResourceManager(String assetsPath) {
        this.assetsPath = assetsPath;
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
    public void loadConfiguration() {
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

    @Override
    public <T> void load (String fileName, Class<T> type) {
        super.load(assetsPath + fileName, type);
    }

    @Override
    public synchronized <T> void load (String fileName, Class<T> type, AssetLoaderParameters<T> parameter) {
        super.load(assetsPath + fileName, type, parameter);
    }

    @Override
    public synchronized <T> T get (String fileName){
        return super.get(assetsPath + fileName);
    }

    @Override
    public synchronized <T> T get (String fileName, Class<T> type){
        return super.get(assetsPath + fileName, type);
    }

    @Override
    public synchronized <T> T get (String fileName , boolean required){
        return super.get(assetsPath + fileName, required);
    }

    @Override
    public synchronized <T> T get (String fileName, Class<T> type, boolean required){
        return super.get(assetsPath + fileName, type, required);
    }


}
