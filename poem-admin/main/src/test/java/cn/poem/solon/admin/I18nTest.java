package cn.poem.solon.admin;

import cn.poem.solon.admin.system.domain.dto.PoemI18nDTO;
import cn.poem.solon.admin.system.domain.query.PoemI18nPageQuery;
import cn.poem.solon.admin.system.domain.vo.PoemI18nVo;
import cn.poem.solon.admin.system.mapper.PoemI18nMapper;
import com.mybatisflex.core.paginate.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.noear.solon.annotation.Inject;
import org.noear.solon.test.SolonJUnit5Extension;

import java.util.Arrays;
import java.util.Map;

@ExtendWith(SolonJUnit5Extension.class)
@Slf4j
public class I18nTest {
    @Inject
    private PoemI18nMapper poemI18nMapper;

    @Test
    public void i18nPage() {
        PoemI18nDTO admin = new PoemI18nDTO().setI18nTag("admin");

        PoemI18nPageQuery poemI18nPageQuery = new PoemI18nPageQuery()
                .setI18nKeys(Arrays.asList("en", "zh-CN"))
                .setPoemI18nDTO(admin);
        Page<Map> page = poemI18nMapper.page(poemI18nPageQuery);
        log.debug(String.valueOf(page));
        assert page.getRecords().get(0).get("en") != null &&
                page.getRecords().get(0).get("zh-CN") != null;
    }

    @Test
    public void i18nInfo() {
        PoemI18nDTO admin = new PoemI18nDTO().setI18nTag("admin").setI18nKey("1");

        PoemI18nPageQuery poemI18nPageQuery = new PoemI18nPageQuery()
                .setI18nKeys(Arrays.asList("en", "zh-CN"))
                .setPoemI18nDTO(admin);
        PoemI18nVo info = poemI18nMapper.info(poemI18nPageQuery);
        log.debug(String.valueOf(info));
        assert info != null;
    }
}
