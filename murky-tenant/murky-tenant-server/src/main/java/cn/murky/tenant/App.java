package cn.murky.tenant;


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
            app.onEvent(HttpServerConfigure.class, e -> {
                e.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
            });
        });
    }
}