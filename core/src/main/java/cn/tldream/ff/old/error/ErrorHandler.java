package cn.tldream.ff.old.error;

public interface ErrorHandler {
    void onModuleError(String moduleName, Throwable e);
}
