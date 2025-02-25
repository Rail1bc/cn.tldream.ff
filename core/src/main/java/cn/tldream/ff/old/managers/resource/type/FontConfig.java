package cn.tldream.ff.old.managers.resource.type;

import cn.tldream.ff.old.enums.ResourceKind;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;
import java.util.Map;

public class FontConfig implements ResourcesType{
    @Override
    public ResourcesType init() {
        JsonReader json = new JsonReader();
        JsonValue jsonData = json.parse(Gdx.files.internal("config/type/font.json"));

        for(JsonValue category = jsonData.getChild("font"); category != null; category = category.next()) {
            String categoryName = category.name();
            Map<String, String> categoryMap = new HashMap<>();

            for(JsonValue resource = category.child; resource != null; resource = resource.next()) {
                categoryMap.put(resource.name(), resource.getString("path"));
            }
            map.put(categoryName, categoryMap);
        }
        return this;
    }

    @Override
    public String getPath(String kind, String name) {
        return map.get(kind).get(name);
    }

    @Override
    public String getPath(ResourceKind kind, String name) {
        return map.get(kind.getValue()).get(name);
    }
}
