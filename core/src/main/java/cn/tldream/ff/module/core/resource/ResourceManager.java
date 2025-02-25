package cn.tldream.ff.module.core.resource;

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


public class ResourceManager extends AssetManager{
    private final String assetsPath; // 资源目录路径

    public ResourceManager(String assetsPath) {
        this.assetsPath = assetsPath;
        initialize();
    }

    /*初始化资源管理器*/
    private void initialize() {
        loadAssets();
        freeTypeFontLoader();
    }

    /*强制加载核心资源*/
    private void loadAssets() {

        load("ui/uiskin.json",Skin.class);
        load("logos/libgdx.png",Texture.class);
        load("logos/tld_p1.png",Texture.class);
        load("logos/tld_p2.png",Texture.class);
        finishLoading();
    }

    /*设置FreeType 字体加载器*/
    private void freeTypeFontLoader() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
    }

    /*重写父类方法，自动拼接资源路径*/
    @Override
    public <T> T get(String fileName, Class<T> type) {
        return super.get(assetsPath + fileName, type);
    }

    @Override
    public <T> T get(String fileName, Class<T> type, boolean required) {
        return super.get(assetsPath + fileName, type, required);
    }


    @Override
    public synchronized <T> void load (String fileName, Class<T> type){
        super.load(assetsPath + fileName, type);
    }

    @Override
    public synchronized <T> void load (String fileName, Class<T> type, AssetLoaderParameters<T> parameter){
        super.load(assetsPath + fileName, type, parameter);
    }

    @Override
    public synchronized boolean contains(String fileName) {
        return super.contains(assetsPath + fileName);
    }

    @Override
    public synchronized boolean isLoaded(String fileName) {
        return super.isLoaded(assetsPath + fileName);
    }

    @Override
    public synchronized void unload(String fileName) {
        super.unload(assetsPath + fileName);
    }
}
