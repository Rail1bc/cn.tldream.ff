package cn.tldream.ff.old.screens.ui;

//组件生命周期控制
public interface StatefulComponent {
    void onResume();    // 界面恢复时调用
    void onPause();     // 界面被覆盖时调用
    void onResize(int width, int height); // 屏幕尺寸变化
}
