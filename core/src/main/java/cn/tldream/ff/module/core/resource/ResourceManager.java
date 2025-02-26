package cn.tldream.ff.module.core.resource;

import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.descriptor.ResourceDescriptor;
import cn.tldream.ff.module.core.resource.descriptor.ResourceType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 资源管理器
 * 继承AssetManager，实现加载资源路径的拼接
 * 预计实现功能：异步加载资源组
 *
 * */
public class ResourceManager extends AssetManager{
    private final String className = "资源管理器";
    private final String assetsPath;
    private final ConfigModule configModule = ConfigModule.getInstance();
    private FreetypeFontLoader.FreeTypeFontLoaderParameter parameter;
    private static AbsoluteFileHandleResolver resolver;

    public ResourceManager(String assetsPath) {
        super(resolver = new AbsoluteFileHandleResolver() {
            @Override
            public FileHandle resolve(String fileName) {
                // 统一在此处做单次路径拼接
                if (fileName.startsWith(assetsPath) || fileName.startsWith(assetsPath.replace('\\','/'))) {
                    return super.resolve(fileName);
                }
                else return super.resolve(assetsPath + fileName);
            }
        });
        this.assetsPath = assetsPath;
        setLoaders();
        freeTypeFontLoader();
    }

    /*初始化资源管理器*/
    public void initialize() {
        Gdx.app.log(className, "初始化");
        loadAssets();
    }

    /*设置自定义加载器*/
    private void setLoaders() {
        setLoader(Properties.class, new PropertiesLoader(resolver));
        setLoader(JsonValue.class, new JsonValueLoader(resolver));
    }

    /*强制加载核心资源*/
    private void loadAssets() {
        Gdx.app.log(className, "强制加载核心资产");
        ResourceDescriptor resd = configModule.getResource("vanilla:texture.logo.engine_logo");
        this.load("ui/uiskin.json", Skin.class);
        load(resd.getPath(), resd.getType());
        load("logo/tld_p1.png",Texture.class);
        load("logo/tld_p2.png",Texture.class);
        finishLoading();
        Gdx.app.log(className, "核心资产加载完成");
    }

    /*设置FreeType 字体加载器*/
    private void freeTypeFontLoader() {
        FileHandleResolver resolver = new AbsoluteFileHandleResolver();
        setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter.fontParameters.incremental = true;
        parameter.fontFileName = assetsPath + "font/simhei.ttf";
    }

    /*获取字体*/
    public BitmapFont getFont(int size) {
        Gdx.app.log(className, "获取字体");
        parameter.fontParameters.size = size;

        // 加载字体
        load(assetsPath + "font/simhei.ttf", BitmapFont.class , parameter);

        finishLoading();

        // 获取字体
        BitmapFont myBigFont = get(assetsPath + "font/simhei.ttf", BitmapFont.class);
        // 设置字体支持Markup
        myBigFont.getData().markupEnabled = true;

        return myBigFont;
    }

    /*获取文件*/
    public FileHandle getFileHandle(String fileName) {
        return Gdx.files.absolute(assetsPath + fileName);
    }

    /*批量加载资源*/
    public <T> void loadAssets(Map<String,Class<T>> assetsMap){
        for(Map.Entry<String,Class<T>> entry : assetsMap.entrySet()){
            load(entry.getKey(),entry.getValue());
        }
    }

    /*批量获取资源*/
    public <T> Map<String,T> getAssets(Map<String,Class<T>> assetsMap){
        Map<String,T> assets = new ConcurrentHashMap<>();
        for(Map.Entry<String,Class<T>> entry : assetsMap.entrySet()){
            assets.put(entry.getKey(),get(entry.getKey(),entry.getValue()));
        }
        return assets;
    }
}
