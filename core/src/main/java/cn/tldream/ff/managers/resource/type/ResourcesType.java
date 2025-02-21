package cn.tldream.ff.managers.resource.type;

import cn.tldream.ff.enums.ResourceKind;

import java.util.HashMap;
import java.util.Map;

public interface ResourcesType {
    public Map<String, Map<String, String>> map = new HashMap<>();

    public ResourcesType init();

    public String getPath(String kind,String name);

    public String getPath(ResourceKind kind,String name);
}
