package cn.murky.tenant;


import cn.dev33.satoken.SaManager;
import cn.murky.tenant.core.MurkySaTokenDao;
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

            app.onEvent(SaManager.class, e -> {
                Solon.context().getBeanAsync(MurkySaTokenDao.class, SaManager::setSaTokenDao);
            });

        });
    }
}