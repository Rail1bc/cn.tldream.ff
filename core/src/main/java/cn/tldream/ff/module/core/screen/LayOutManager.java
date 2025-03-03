package cn.tldream.ff.module.core.screen;


import cn.tldream.ff.module.core.config.ConfigKey;
import cn.tldream.ff.module.core.config.ConfigModule;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.util.Map;

public class LayOutManager {
    private final String className = "布局管理器";
    private UIModule uiModule;
    private ConfigModule configModule;
    private final Map<String, Table> tableMap;

    /*
     * 生命周期方法
     * 由样式管理模块调用
     * */

    /*构造函数*/
    public LayOutManager(Map<String, Table> tableMap){
        Gdx.app.debug(className, "实例化");
        this.tableMap = tableMap;
    }

    /*依赖注入*/
    public void receiveDependency(UIModule uiModule) {
        Gdx.app.debug(className, "依赖注入");
        this.uiModule = uiModule;
        this.configModule = ConfigModule.getInstance();
    }

    /*预初始化*/
    public void preInit(){
        Gdx.app.debug(className, "预初始化");
        createTable();
    }

    /*主初始化*/
    public void init() {
        Gdx.app.debug(className, "主初始化");
    }

    /*
    * 私有方法
    * */

    /*创建桌面*/
    private void createTable(){
        createMainMenuTable();
    }

    /*创建开始桌面*/
    private void createMainMenuTable(){
        Table table = new Table();
        table.setSize(200,600);
        table.setPosition(200,0 , Align.left);
        table.setFillParent(false);
        table.right();
        table.setDebug(true);
//        table.align(Align.topRight);
        table.defaults().spaceBottom(20).width(200).fill(false);
        Button btn = uiModule.getUI("btn_start");
        table.add(btn).height(50).fill(false);
        table.row();
        btn = uiModule.getUI("btn_setting");
        table.add(btn).height(50).fill(false);
        table.row();
        btn = uiModule.getUI("btn_exit");
        table.add(btn).height(50).fill(false);

        tableMap.put("mainMenu", table);
    }



}
