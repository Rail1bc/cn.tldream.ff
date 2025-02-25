package cn.tldream.ff.old.module;

import cn.tldream.ff.old.managers.ModuleManager;
import cn.tldream.ff.old.managers.StyleManager;

public class StyleModule implements GameModule{
    private ResourceModule resourceModule;
    private StyleManager styleManager;

    @Override
    public String[] getDependencies() { return new String[]{"resource"}; }

    @Override
    public void preInit() {

    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {
        // 通过模块管理器获取依赖
        this.resourceModule = ModuleManager.getModule("resource",ResourceModule.class);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void dispose() {

    }
}
