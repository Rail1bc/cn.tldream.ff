package cn.tldream.ff.module.core.resource;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件加载器
 *
 * */
public class PropertiesLoader extends SynchronousAssetLoader<Properties, PropertiesLoader.PropertiesParameter> {
    private Properties properties;

    public PropertiesLoader (FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Properties load(AssetManager assetManager, String fileName, FileHandle file, PropertiesParameter parameter) {
        Properties props = new Properties();
        try (InputStream input = file.read()) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("加载 Properties 文件失败: " + fileName, e);
        }
        return props;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, PropertiesParameter parameter) {
        return null;
    }


    static public class PropertiesParameter extends AssetLoaderParameters<Properties> {
    }
}
