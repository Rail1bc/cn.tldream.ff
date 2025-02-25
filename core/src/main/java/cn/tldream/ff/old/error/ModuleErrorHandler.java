package cn.tldream.ff.old.error;

import com.badlogic.gdx.Gdx;

public class ModuleErrorHandler implements ErrorHandler {
    @Override
    public void onModuleError(String moduleName, Throwable e) {
        Gdx.app.log(moduleName ,  "模块初始化失败" + e.getMessage());
    }
}
