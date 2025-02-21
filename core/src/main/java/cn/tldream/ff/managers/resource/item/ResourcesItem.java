package cn.tldream.ff.managers.resource.item;

import cn.tldream.ff.enums.ResourceItem;

import java.util.HashMap;
import java.util.Map;

//
public interface ResourcesItem {
    public Map<ResourceItem, Map<String, String>> map = new HashMap<>();
}
