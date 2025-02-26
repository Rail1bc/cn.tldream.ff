package cn.tldream.ff.module.core.resource;

import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.descriptor.ResourceDescriptor;
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
import com.badlogic.gdx.utils.JsonValue;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 资源管理器
 * 继承AssetManager，实现加载资源路径的拼接
 * 预计实现功能：异步加载资源组
 * 生命周期：
 * 资源管理模块实例化时实例化
 * 资源管理模块初始化时初始化
 * 资源管理模块处置时处置
 *
 * */
public class ResourceManager extends AssetManager{
    private final String className = "资源管理器";
    private final String assetsPath; // 资源目录路径
    private final ConfigModule configModule = ConfigModule.getInstance(); // 配置管理模块
    private FreetypeFontLoader.FreeTypeFontLoaderParameter parameter; // FreeType字体加载器参数
    private static AbsoluteFileHandleResolver resolver; // 资源路径解析器

    public ResourceManager(String assetsPath) {
        super(resolver = new AbsoluteFileHandleResolver() {
            // 重写路径解析器，拼接资源目录路径
            @Override
            public FileHandle resolve(String fileName) {
                if (fileName.startsWith(assetsPath) || fileName.startsWith(assetsPath.replace('\\','/'))) return super.resolve(fileName);
                else return super.resolve(assetsPath + fileName);
            }
        });
        this.assetsPath = assetsPath;
        setLoaders();
        freeTypeFontLoader();
    }

    /*根据id，将资源加入加载队列*/
    public void load(String id){
        ResourceDescriptor resd = configModule.getResource(id);
        load(resd.getPath(),resd.getType());
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
        load("vanilla:skin.uiskin.default");
        load("vanilla:texture.logo.engine_logo");
        load("vanilla:texture.logo.tld_p1");
        load("vanilla:texture.logo.tld_p2");
        finishLoading();
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
