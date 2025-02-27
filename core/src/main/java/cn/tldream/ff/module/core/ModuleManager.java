package cn.tldream.ff.module.core;

import cn.tldream.ff.module.GameModule;
import com.badlogic.gdx.Gdx;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模块管理器
 * 生命周期：
 * 主类Create方法调用时，模块管理器实例化、初始化
 * 主类销处置，模块管理器处置
 * 工作内容：
 * 管理全部模块生命周期，并且确保模块初始化顺序
 * 工作流程：
 * 在主类Create方法中注册模块
 * 在主类调用初始化方法，模块初始化
 * 初始化流程，首先进行排序，然后依赖注入
 * 最后模块初始化
 * */
public class ModuleManager {
    private static final ModuleManager instance = new ModuleManager();
    private static final String className = "模块管理器";
    private static final Map<String, GameModule> modules = new ConcurrentHashMap<>(); // 模块图
    private static List<GameModule> initializedModules; // 模块拓扑排序列表

    public static ModuleManager getInstance() {
        return instance;
    }

    /*注册模块*/
    public ModuleManager register(String name, GameModule module) {
        if (modules.containsKey(name)) {
            throw new IllegalArgumentException("重名模块: " + name);
        }
        modules.put(name, module);
        return this;
    }

    /*获取模块*/
    public static <T extends GameModule>  T getModule(String name, Class<T> type) {
        return Optional.ofNullable(modules.get(name))
            .filter(type::isInstance)
            .map(type::cast).orElseThrow();
    }


    /*初始化全部模块*/
    public void initialize() {
        Gdx.app.debug(className, "初始化");
        initializedModules = topologicalSort();
        initializedModules.forEach(GameModule::receiveDependency);
        initializedModules.forEach(GameModule::preInit);
        initializedModules.forEach(GameModule::init);
        initializedModules.forEach(GameModule::postInit);
    }


    /*处置全部模块*/
    public void dispose() {
        Gdx.app.debug(className, "开始处置");
        List<GameModule> reverse = new ArrayList<>(initializedModules);
        Collections.reverse(reverse);
        reverse.forEach(GameModule::dispose);
        initializedModules.clear();
    }


    /*模块排序*/
    private List<GameModule> topologicalSort() {
        Gdx.app.debug(className, "依赖关系拓扑排序");
        // 实现基于依赖关系的拓扑排序算法
        // 确保依赖模块先初始化
        // 使用Kahn算法实现拓扑排序
        Map<String, Integer> inDegree = new ConcurrentHashMap<>();
        Map<String, List<String>> adjList = new ConcurrentHashMap<>();
        Queue<String> queue = new LinkedList<>();

        // 初始化数据结构
        modules.keySet().forEach(name -> {
            inDegree.put(name, 0);
            adjList.put(name, new ArrayList<>());
        });

        // 构建图
        modules.forEach((name, module) -> {
            for (String dep : module.getDependencies()) {
                if (!modules.containsKey(dep)) {
                    throw new IllegalStateException("未找到模块: " + dep);
                }
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
            GameModule module = modules.get(current);

            // 注入依赖
            for (String dep : module.getDependencies()) {

            }

            sorted.add(module);
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
