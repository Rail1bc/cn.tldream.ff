package cn.tldream.ff.old.enums;

public enum ResourceKind {
    logo("logo"),
    heightmap("heightmap"),
    uiskin("uiskin"),
    ttf("ttf");

    private final String value;

    ResourceKind(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
