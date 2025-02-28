# **说明文档**

## 一、命名规范：

- ### 包名全小写
```text
    cn.tldream.ff.module.core.resource;
```
- ### 类名大驼峰
```text
    cn.tldream.ff.module.core.resource.ResourceModule;
```
- ### 变量名小驼峰
```java
    private final String className = "资源管理模块";
    private final ResourceManager resourceManager; //资源管理器
```
- ### 方法名小驼峰
```java
    public void preInit();
    public void innit();
    public void postInit();
```
## 二、注释规范：

### xxx管理模块注释规范
- ### 构造函数、生命周期方法简单注释
- ### 特有的功能方法需要javadoc注释
```java
    /*
    * 资源管理模块
    * 依赖模块：配置管理模块
    * 生命周期：由主类实例化，并注册进模块管理器
    * ......
    * 工作内容：
    * ......
    * 工作流程：
    * ......
    * */
    public class ResourceModule implements GameModule {
        private final String className = "资源管理模块";
        private final ResourceManager resourceManager; //资源管理器
        private ConfigModule configModule; // 配置管理模块
        private boolean isInitialized = false; // 初始化状态

        /*
        * 生命周期方法
        * 由模块管理器调用
        * */

        /*构造函数*/
        public ResourceModule(String assetsPath);

        /*获取优先级*/
        @Override
        public int getInitPriority();
        
        // ......

        /*
        * 暴露服务接口
        * */

        /**
        * 同步加载已经在加载队列的资源，阻塞
        * */
        public void finishLoading();
        
        // ......
    }
```
- ### xxx管理器注释规范
```java
    //同上
```
- ## 三、项目进度
#### 已完成 √  |  待完善 *
#### 刚起头 ?  |  还没写 X
### 模块化
- #### 模块管理器 √
- #### 配置管理模块 *
- #### 资源管理模块 *
- #### 样式管理模块 x
- #### UI组件管理模块 x
- #### 布局管理模块 x
- #### 屏幕管理模块 ?
