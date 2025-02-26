package cn.tldream.ff.module.core.resource;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.io.IOException;
import java.io.InputStream;


public class JsonValueLoader extends SynchronousAssetLoader<JsonValue, JsonValueLoader.JsonValueParameter> {
    private JsonValue jsonData;

    public JsonValueLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, JsonValueParameter parameter) {
        return null;
    }

    @Override
    public JsonValue load(AssetManager assetManager, String fileName, FileHandle file, JsonValueParameter parameter) {
        JsonValue jsonData;
        try (InputStream input = file.read()) {
            JsonReader jsonReader = new JsonReader();
            jsonData = jsonReader.parse(input);
        } catch (IOException e) {
            throw new RuntimeException("加载 JsonValue 文件失败: " + fileName, e);
        }
        return jsonData;
    }


    static public class JsonValueParameter extends AssetLoaderParameters<JsonValue> {
    }
}
