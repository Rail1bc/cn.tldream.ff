package cn.tldream.ff.old.error;

import com.badlogic.gdx.Gdx;

public class CyclicDependencyException implements ErrorHandler{

    @Override
    public void onModuleError(String moduleName, Throwable e) {
        Gdx.app.log(moduleName,"模块依赖关系包含循环" + e.getMessage());
    }
}
