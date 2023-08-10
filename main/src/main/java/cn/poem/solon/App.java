package cn.poem.solon;

import cn.poem.solon.utils.SecurityUtil;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Import;
import org.noear.solon.annotation.SolonMain;
import org.noear.solon.core.event.AppLoadEndEvent;

@SolonMain
@Import(scanPackages = "cn.poem")
public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args , app -> {
//            app.onEvent(AppLoadEndEvent(app),EventListener<>)
//            //初始化SecurityUtil
//            new SecurityUtil();
        });
    }

}
