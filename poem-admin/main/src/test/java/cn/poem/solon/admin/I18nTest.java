package cn.poem.solon.admin;

import cn.poem.solon.admin.system.domain.dto.PoemI18nPageDTO;
import cn.poem.solon.admin.system.domain.query.PoemI18nPageQuery;
import cn.poem.solon.admin.system.mapper.PoemI18nMapper;
import com.mybatisflex.core.paginate.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.noear.solon.annotation.Import;
import org.noear.solon.annotation.Inject;
import org.noear.solon.test.SolonJUnit5Extension;

import java.util.Arrays;
import java.util.Map;

@Import(propertySource = "classpath:app-dev.yml")
@ExtendWith(SolonJUnit5Extension.class)
@Slf4j
public class I18nTest {
    @Inject
    private PoemI18nMapper poemI18nMapper;

    @Test
    public void i18nPage(){
        PoemI18nPageDTO admin = new PoemI18nPageDTO().setI18nTag("admin");

        PoemI18nPageQuery poemI18nPageQuery = new PoemI18nPageQuery()
                .setI18nKeys(Arrays.asList("en","zh"))
                .setPoemI18nPageDTO(admin);
        Page<Map<String, String>> page = poemI18nMapper.page(poemI18nPageQuery);
        log.debug(String.valueOf(page));
    }
}
