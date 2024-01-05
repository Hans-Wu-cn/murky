package cn.murky.admin.flex.config;

import cn.murky.admin.common.entity.BaseEntity;
import cn.murky.admin.flex.MurkyFlexImpl;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.mybatis.FlexConfiguration;
import org.apache.ibatis.solon.annotation.Db;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;


/**
 * mybatis-flex配置类
 *
 * @author hans
 */
@Configuration
public class FlexConfig {

    //调整 db1 的配置（如：增加插件）// (配置可以解决的，不需要这块代码)
    @Bean
    public void dbCfg(@Db FlexConfiguration cfg,
                        @Db FlexGlobalConfig globalConfig) {

        //1.初始化数据填充器
        MurkyFlexImpl murkyFlex = new MurkyFlexImpl();
        initListener(globalConfig,murkyFlex);
//        DialectFactory.registerDialect(globalConfig.getDbType(),murkyFlex);
    }

    /**
     * 初始化字段填充器
     */
    private void initListener(FlexGlobalConfig globalConfig, MurkyFlexImpl murkyFlex){

        //为 BaseEntity 注册 insertListner
        globalConfig.registerInsertListener(murkyFlex, BaseEntity.class);

        //为 BaseEntity 注册 updateListener
        globalConfig.registerUpdateListener(murkyFlex, BaseEntity.class);
    }
}
