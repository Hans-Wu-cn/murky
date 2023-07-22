package cn.poem.core.config;


import cn.poem.core.utils.ApiResult;
import com.github.xiaoymin.knife4j.solon.extension.OpenApiExtensionResolver;
import io.swagger.models.Contact;
import io.swagger.models.Scheme;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;
import org.noear.solon.docs.DocDocket;
import org.noear.solon.docs.models.ApiInfo;

/***
 * 接口文档配置类
 *
 * @author hans
 */
@Configuration
public class DocConfig {
    // knife4j 的配置，由它承载
    @Inject
    OpenApiExtensionResolver openApiExtensionResolver;

    /**
     * 简单点的
     */
    @Bean("appApi")
    public DocDocket appApi() {
        //根据情况增加 "knife4j.setting" （可选）
        return new DocDocket()
                .basicAuth(openApiExtensionResolver.getSetting().getBasic())
                .vendorExtensions(openApiExtensionResolver.buildExtensions())
                .groupName("app端接口")
                .schemes("Scheme.HTTP")
                .globalResult(ApiResult.class)
                .globalResponseInData(true)
                .apis("com.swagger.demo.controller.app")
                .securityDefinitionInHeader("token");

    }

    /**
     * 丰富点的
     */
    @Bean("adminApi")
    public DocDocket adminApi() {
        return new DocDocket()
                .groupName("admin端接口")
                .info(new ApiInfo().title("在线文档")
                        .description("在线API文档")
                        .termsOfService("https://gitee.com/noear/solon")
                        .contact(new Contact().name("demo")
                                .url("https://gitee.com/noear/solon")
                                .email("837713748@qq.com"))
                        .version("1.0"))
                .schemes(Scheme.HTTP.toValue(), Scheme.HTTPS.toValue())
                .globalResponseInData(true)
                .globalResult(ApiResult.class)
                .apis("cn.poem.solon.admin.system.controller") //可以加多条，以包名为单位
                ;//.securityDefinitionInHeader("token");

    }
}
