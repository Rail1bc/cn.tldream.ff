package cn.tldream.ff.enums;

public enum ResourceType {
    TEXTURE("texture"),
    SKIN("skin"),
    FONT("font");

    private final String value;

    ResourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
