package cn.tldream.ff.managers.resource.config;

import cn.tldream.ff.enums.configType;

import java.util.HashMap;
import java.util.Map;

public interface ConfigsType {
    public Map<configType, Map<String, String>> map = new HashMap<>();
}
