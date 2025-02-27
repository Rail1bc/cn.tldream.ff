package cn.tldream.ff.module.core.resource;

import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.descriptor.ResourceDescriptor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 资源管理器
 * 依赖模块：配置管理模块
 * 生命周期：由资源管理模块实例化并管理
 * 实例化 模块实例化时进行
 * 依赖注入 模块依赖注入时进行
 * 预初始化 模块预初始化时进行
 * 主初始化 模块主初始化时进行
 * 工作内容：
 * 提供加载资源的功能
 * 通过资源id,从配置管理器中获取资源描述符，并加载资源
 * 工作流程：
 * 实例化时，设置自定义加载器
 * 依赖注入时，获取配置管理模块实例
 * 预初始化时，什么都不做，实际上此时配置管理模块已经完成初始化，拥有完整功能
 * 主初始化时，强制加载核心资源
 * 注意事项：
 * 依赖注入后，理论上拥有完整功能
 * 但实际上，此时配置管理模块尚未完全初始化，此时加载任何除核心资源以外的资源，都会报错
 * 配置管理模块预初始化完成后，真正拥有完整功能
 * 主初始化，强制进行核心资源加载
 * */
public class ResourceManager extends AssetManager{
    private final String className = "资源管理器";
    private final String assetsPath; // 资源目录路径
    private ConfigModule configModule;
    private FreetypeFontLoader.FreeTypeFontLoaderParameter parameter; // FreeType字体加载器参数
    private static AbsoluteFileHandleResolver resolver; // 资源路径解析器

    /*
     * 生命周期方法
     * 由配置管理模块调用
     * */

    /*构造函数*/
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
        setLoaders(); // 设置自定义加载器
    }

    /*依赖注入*/
    public void setConfigModule(ConfigModule configModule) {
        this.configModule = configModule;
    }

    /*预初始化*/
    public void preInit(){

    }


    /*主初始化*/
    public void init() {
        Gdx.app.debug(className, "主初始化");
        loadAssets(); // 强制加载核心资源
        freeTypeFontLoader(); // 设置FreeType字体加载器
    }

    /*
    * 服务方法
    * 暴露给资源管理模块
    * */

    /*获取字体*/
    public BitmapFont getFont(int size) {
        Gdx.app.debug(className, "获取字体");
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

    /*根据id，将资源加入加载队列*/
    public void load(String id){
        ResourceDescriptor resd = configModule.getResource(id);
        load(resd.getPath(),resd.getType());
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


    /*
     * 私有功能方法
     * */

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
}
