package cn.poem.solon;

import org.noear.solon.Solon;
import org.noear.solon.annotation.Import;
import org.noear.solon.annotation.SolonMain;

@SolonMain
@Import(scanPackages = "cn.poem")
public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args);
    }

}
