package cn.poem.solon.admin;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.noear.solon.test.SolonJUnit5Extension;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class StringTemplateTest {


    @Test
    public void test(){
        testMessageformate();
        System.out.println();
        testTempalte();
    }
    @Test
    public void testMessageformate() {
        // 开始时间
        long stime = System.currentTimeMillis();
        // 执行时间（1s）
        StringBuilder str=new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            str.append(MessageFormat.format(" {0}",i));
        }
        // 结束时间
        long etime = System.currentTimeMillis();
        // 计算执行时间
        System.out.printf("MessageFormat执行时长：%d 毫秒.", (etime - stime));

    }

    @Test
    public void testTempalte() {
        // 开始时间
        long stime = System.currentTimeMillis();
        // 执行时间（1s）
        StringBuilder str=new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            str.append(STR."""
                    \{i}
                    """);
        }
        // 结束时间
        long etime = System.currentTimeMillis();
        // 计算执行时间
        System.out.printf("Tempalte执行时长：%d 毫秒.", (etime - stime));
    }

    @Test
    public void testTempalte1() {
        Set<Long> set=new HashSet<>();
        set.add(1l);
        set.add(2l);
        set.add(3l);
        set.add(4l);
        // 开始时间
        long stime = System.currentTimeMillis();
        // 执行时间（1s）
        StringBuilder str=new StringBuilder();
        str.append(STR."""
                \{set}
                """);
        // 结束时间
        long etime = System.currentTimeMillis();
        // 计算执行时间
        System.out.printf(str.toString());
    }
}
