package cn.poem.solon.admin;

import org.noear.solon.Solon;
import org.noear.solon.annotation.Import;
import org.noear.solon.annotation.SolonMain;

@SolonMain
@Import(scanPackages = "cn.poem.solon.core")
public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args);
    }

}
