package cn.tldream.ff.module.core.resource;

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


/**
 * 资源管理类
 * 继承AssetManager，实现加载资源路径的拼接
 * 预计实现功能：异步加载资源组
 *
 * */
public class ResourceManager extends AssetManager{
    private final String assetsPath;
    private FreetypeFontLoader.FreeTypeFontLoaderParameter parameter;

    public ResourceManager(String assetsPath) {
        super(new AbsoluteFileHandleResolver() {
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
    }

    /*初始化资源管理器*/
    public void initialize() {
        loadAssets();
        freeTypeFontLoader();
    }

    /*强制加载核心资源*/
    private void loadAssets() {

        this.load("ui/uiskin.json",Skin.class);
        this.load("logos/libgdx.png",Texture.class);
        this.load("logos/tld_p1.png",Texture.class);
        this.load("logos/tld_p2.png",Texture.class);
        finishLoading();
    }

    /*设置FreeType 字体加载器*/
    private void freeTypeFontLoader() {
        FileHandleResolver resolver = new AbsoluteFileHandleResolver();
        setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter.fontParameters.incremental = true;
        parameter.fontFileName = assetsPath + "fonts/simhei.ttf";
    }

    public BitmapFont getFont(int size) {
        parameter.fontParameters.size = size;

        // 加载字体
        load(assetsPath + "fonts/simhei.ttf", BitmapFont.class , parameter);

        finishLoading();

        // 获取字体
        BitmapFont myBigFont = get(assetsPath + "fonts/simhei.ttf", BitmapFont.class);
        // 设置字体支持Markup
        myBigFont.getData().markupEnabled = true;

        return myBigFont;
    }

}
