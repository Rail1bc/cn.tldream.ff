package cn.tldream.ff.module.core.config;

public enum ConfigKey {
    LOG_LEVEL("log.level",3),
    GAME_NAME("game.name","FG"),
    WINDOW_WIDTH("window.width", 1280),
    WINDOW_HEIGHT("window.height", 720),
    FULLSCREEN("window.fullscreen",false),
    LANGUAGE("language","cn");

    private final String value;
    private final Object defaultValue;

    ConfigKey(String valeu, Object defaultValue) {
        this.value = valeu;
        this.defaultValue = defaultValue;
    }

    public String getVal() {
        return value;
    }

    public <T> T getDefault() {
        return (T)defaultValue;
    }
}
