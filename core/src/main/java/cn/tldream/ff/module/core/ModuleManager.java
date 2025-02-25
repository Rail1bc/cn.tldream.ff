package cn.tldream.ff.module.core;

import cn.tldream.ff.module.GameModule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleManager {
    private static final Map<String, GameModule> modules = new ConcurrentHashMap<>(); // 模块图

    // 注册模块
    public ModuleManager register(String name, GameModule module) {
        modules.put(name, module);
        return this;
    }

    // 获取模块
    public static <T extends GameModule> T getModule(String name, Class<T> type) {
        GameModule module = modules.get(name);
        return type.cast(module);
    }


    public void initialize() {
        // 拓扑排序确保初始化顺序
        List<GameModule> sortedModules = topologicalSort();

        sortedModules.forEach(module -> {
            module.init();
        });
    }

    public void dispose() {
        modules.values().forEach(GameModule::dispose);
    }


    private List<GameModule> topologicalSort() {
        // 实现基于依赖关系的拓扑排序算法
        // 确保依赖模块先初始化
        // 使用Kahn算法实现拓扑排序
        Map<String, Integer> inDegree = new HashMap<>();
        Map<String, List<String>> adjList = new HashMap<>();
        Queue<String> queue = new LinkedList<>();

        // 初始化数据结构
        modules.keySet().forEach(name -> {
            inDegree.put(name, 0);
            adjList.put(name, new ArrayList<>());
        });

        // 构建图
        modules.forEach((name, module) -> {
            for (String dep : module.getDependencies()) {
                adjList.get(dep).add(name);
                inDegree.put(name, inDegree.get(name) + 1);
            }
        });

        // 找到入度为0的起点
        inDegree.entrySet().stream()
            .filter(entry -> entry.getValue() == 0)
            .forEach(entry -> queue.add(entry.getKey()));

        // 执行拓扑排序
        List<GameModule> sorted = new ArrayList<>();
        while (!queue.isEmpty()) {
            String current = queue.poll();
            sorted.add(modules.get(current));

            for (String neighbor : adjList.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return sorted;
    }
}
