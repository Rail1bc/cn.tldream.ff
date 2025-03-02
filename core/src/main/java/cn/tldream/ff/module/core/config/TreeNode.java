package cn.tldream.ff.module.core.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TreeNode {
    String name;                    // 节点名称
    Object value;                   // 节点值
    Map<String, TreeNode> children = new ConcurrentHashMap<>(); // 子节点

    // 新增：路径缓存（将完整路径与节点绑定）
    Map<String, TreeNode> pathCache = new ConcurrentHashMap<>();

    // 根据路径添加节点（自动维护缓存）
    public void addNode(String fullPath, Object value) {
        String[] parts = fullPath.split("[:.]"); // 按 : 和 . 分割
        TreeNode current = this;
        StringBuilder pathBuilder = new StringBuilder();

        for (String part : parts) {
            pathBuilder.append(part).append(":");
            current = current.children.computeIfAbsent(part, k -> new TreeNode());
            current.pathCache.put(pathBuilder.toString(), current); // 缓存路径片段
        }
        current.value = value;
        pathCache.put(fullPath, current); // 缓存完整路径
    }

    // 通过完整路径快速查找（O(1)）
    public TreeNode getNodeByPath(String fullPath) {
        return pathCache.get(fullPath);
    }
}
