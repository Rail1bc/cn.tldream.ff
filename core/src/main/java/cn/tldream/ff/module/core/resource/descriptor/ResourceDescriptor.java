package cn.tldream.ff.module.core.resource.descriptor;

public abstract class ResourceDescriptor {
    protected ResourceType type;
    protected String path;

    public ResourceDescriptor(String path){
        this.path = path;
    }

    public <T> Class<T> getType(){
        return (Class<T>) type.getVal();
    }

    public String getPath(){
        return path;
    }

}
