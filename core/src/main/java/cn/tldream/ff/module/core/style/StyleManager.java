package cn.tldream.ff.module.core.style;

import cn.tldream.ff.module.core.config.ConfigModule;
import cn.tldream.ff.module.core.resource.ResourceModule;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class StyleManager {
    private final String className = "样式管理器";
    private ResourceModule resourceModule; // 资源管理模块实例
    private ConfigModule configModule; // 配置管理模块实例

    /*
     * 生命周期方法
     * 由样式管理模块调用
     * */

    /*构造函数*/
    public StyleManager(){

    }

    /*依赖注入*/
    public void receiveDependency(ResourceModule resourceModule) {
        this.resourceModule = resourceModule;
        this.configModule = ConfigModule.getInstance();
    }

    /*预初始化*/
    public void preInit(){
        Gdx.app.debug(className, "预初始化");
        resourceModule.setParameterFillName("vanilla:font.ttf.cn",32);
        // 加载字体
        resourceModule.loadFont("vanilla:font.ttf.cn");

    }

    /*主初始化*/
    public void init() {
        Gdx.app.debug(className, "主初始化");
    }



    /*
    * 服务方法
    * */

    /**
     * 获取字体
     * @return 字体
     * */
    public BitmapFont getFont(){
        // 获取字体
        BitmapFont myBigFont = resourceModule.get("vanilla:font.ttf.cn");
        // 设置字体支持Markup
        myBigFont.getData().markupEnabled = true;

        return myBigFont;
    }
}
