package cn.tldream.ff.module.core;

import cn.tldream.ff.module.GameModule;
import com.badlogic.gdx.Gdx;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 模块管理器
 * 生命周期：
 * 主类Create方法调用时，模块管理器实例化、初始化
 * 主类处置时，模块管理器处置
 * 工作内容：
 * 管理全部模块生命周期，并且确保模块初始化顺序
 * 工作流程：
 * 在主类Create方法中注册模块
 * 在主类调用初始化方法，模块初始化
 * 初始化时，首先进行依赖拓扑排序
 * 排序后，依据排序，依次进行依赖注入
 * 依赖注入后，依次进行预初始化
 * 预初始化后，依次进行主初始化
 * 主初始化后，依次进行后初始化
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
        initializedModules = topologicalSort(); // 拓扑排序
        initializedModules.forEach(GameModule::receiveDependency); // 依赖注入
        initializedModules.forEach(GameModule::preInit); // 预初始化
        initializedModules.forEach(GameModule::init); // 主初始化
        initializedModules.forEach(GameModule::postInit); // 后初始化
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

        // 构建邻接表
        Map<String, List<String>> adj = new HashMap<>();
        for (String moduleName : modules.keySet()) {
            adj.put(moduleName, new ArrayList<>());
        }
        for (String module : modules.keySet()) {
            for (String dep : modules.get(module).getDependencies()) {
                adj.get(dep).add(module);
            }
        }

        // 使用Tarjan算法找到所有SCC
        TarjanResult tarjanResult = tarjan(modules.keySet(), adj);
        List<List<String>> sccs = tarjanResult.sccs;
        Map<String, Integer> sccMap = tarjanResult.sccMap;

        // 构建SCC之间的边
        Map<Integer, Set<Integer>> sccAdj = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();

        for (int i = 0; i < sccs.size(); i++) {
            sccAdj.put(i, new HashSet<>());
            inDegree.put(i, 0);
        }

        for (String u : modules.keySet()) {
            for (String v : adj.get(u)) {
                int sccU = sccMap.get(u);
                int sccV = sccMap.get(v);
                if (sccU != sccV) {
                    if (!sccAdj.get(sccU).contains(sccV)) {
                        sccAdj.get(sccU).add(sccV);
                        inDegree.put(sccV, inDegree.getOrDefault(sccV, 0) + 1);
                    }
                }
            }
        }

        // 拓扑排序SCC图
        Queue<Integer> queue = new LinkedList<>();
        for (int sccId : inDegree.keySet()) {
            if (inDegree.get(sccId) == 0) {
                queue.add(sccId);
            }
        }

        List<Integer> topoOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            int sccId = queue.poll();
            topoOrder.add(sccId);
            for (int neighbor : sccAdj.getOrDefault(sccId, new HashSet<>())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // 收集每个SCC的模块并排序
        Map<Integer, List<GameModule>> sccModules = new HashMap<>();
        for (String moduleName : modules.keySet()) {
            int sccId = sccMap.get(moduleName);
            sccModules.computeIfAbsent(sccId, k -> new ArrayList<>()).add(modules.get(moduleName));
        }
        for (List<GameModule> modules : sccModules.values()) {
            modules.sort(Comparator.comparingInt(GameModule::getInitPriority));
        }

        // 构建结果列表
        List<GameModule> result = new ArrayList<>();
        for (int sccId : topoOrder) {
            List<GameModule> modules = sccModules.get(sccId);
            if (modules != null) {
                result.addAll(modules);
            }
        }

        for(GameModule module : result){
            Gdx.app.debug("排序", module.getClass().getSimpleName());
        }

        return result;
    }



    private TarjanResult tarjan(Set<String> nodes, Map<String, List<String>> adj) {
        int[] index = {0};
        Map<String, Integer> indexMap = new HashMap<>();
        Map<String, Integer> lowLink = new HashMap<>();
        Deque<String> stack = new ArrayDeque<>();
        Set<String> onStack = new HashSet<>();
        List<List<String>> sccs = new ArrayList<>();
        Map<String, Integer> sccMap = new HashMap<>();

        for (String node : nodes) {
            if (!indexMap.containsKey(node)) {
                strongConnect(node, adj, indexMap, lowLink, stack, onStack, sccs, sccMap, index);
            }
        }

        return new TarjanResult(sccs, sccMap);
    }

    private void strongConnect(String v, Map<String, List<String>> adj,
                               Map<String, Integer> indexMap, Map<String, Integer> lowLink,
                               Deque<String> stack, Set<String> onStack,
                               List<List<String>> sccs, Map<String, Integer> sccMap, int[] indexHolder) {
        indexMap.put(v, indexHolder[0]);
        lowLink.put(v, indexHolder[0]);
        indexHolder[0]++;
        stack.push(v);
        onStack.add(v);

        for (String w : adj.getOrDefault(v, Collections.emptyList())) {
            if (!indexMap.containsKey(w)) {
                strongConnect(w, adj, indexMap, lowLink, stack, onStack, sccs, sccMap, indexHolder);
                lowLink.put(v, Math.min(lowLink.get(v), lowLink.get(w)));
            } else if (onStack.contains(w)) {
                lowLink.put(v, Math.min(lowLink.get(v), indexMap.get(w)));
            }
        }

        if (lowLink.get(v).equals(indexMap.get(v))) {
            List<String> scc = new ArrayList<>();
            String w;
            do {
                w = stack.pop();
                onStack.remove(w);
                scc.add(w);
                sccMap.put(w, sccs.size());
            } while (!w.equals(v));
            sccs.add(scc);
        }
    }

    static class TarjanResult {
        List<List<String>> sccs;
        Map<String, Integer> sccMap;

        TarjanResult(List<List<String>> sccs, Map<String, Integer> sccMap) {
            this.sccs = sccs;
            this.sccMap = sccMap;
        }
    }
}
