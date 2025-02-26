package cn.tldream.ff.module.core.resource.descriptor;

public class JsonDes extends ResourceDescriptor{

    /*重写构造函数*/
    public JsonDes(String path) {
        super(path);
        this.type = ResourceType.json;
    }


}
