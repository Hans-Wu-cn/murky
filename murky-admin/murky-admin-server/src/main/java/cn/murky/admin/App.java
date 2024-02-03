package cn.murky.admin;

import org.noear.solon.Solon;
import org.noear.solon.annotation.Import;
import org.noear.solon.annotation.SolonMain;
import org.noear.solon.boot.http.HttpServerConfigure;

import java.util.concurrent.Executors;

@SolonMain
@Import(scanPackages = "cn.murky")
public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args, app -> {
            // 使用socketd服务
            app.enableSocketD(true);
            app.onEvent(HttpServerConfigure.class, e -> {
                // 使用虚拟线程
                e.setExecutor(Executors.newVirtualThreadPerTaskExecutor());

            });
        });
    }

}
