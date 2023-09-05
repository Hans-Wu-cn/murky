package cn.poem.solon.core.config;


import cn.poem.solon.core.enums.ApiResultEnum;
import cn.poem.solon.core.utils.ApiResult;
//import com.github.xiaoymin.knife4j.solon.extension.OpenApiExtensionResolver;
import io.swagger.models.Scheme;
import org.noear.solon.annotation.*;
import org.noear.solon.core.handle.Context;
import org.noear.solon.docs.DocDocket;
import org.noear.solon.docs.models.ApiContact;
import org.noear.solon.docs.models.ApiInfo;
import org.noear.solon.docs.openapi2.OpenApi2Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/***
 * 接口文档配置类
 *
 * @author hans
 */
@Controller
@Configuration
public class DocConfig{
    /**
     * swagger 获取分组信息
     */
    @Produces("application/json; charset=utf-8")
    @Mapping("swagger-resources")
    public String resources() throws IOException {
        return OpenApi2Utils.getApiGroupResourceJson();
    }

    /**
     * swagger 获取分组接口数据
     */
    @Produces("application/json; charset=utf-8")
    @Mapping("swagger/v2")
    public String api(Context ctx, String group) throws IOException {
        return OpenApi2Utils.getApiJson(ctx, group);
    }
    // knife4j 的配置，由它承载
//    @Inject
//    OpenApiExtensionResolver openApiExtensionResolver;
//
//    /**
//     * 简单点的
//     */
//    @Bean("appApi")
//    public DocDocket appApi() {
//        //根据情况增加 "knife4j.setting" （可选）
//        return new DocDocket()
//                .basicAuth(openApiExtensionResolver.getSetting().getBasic())
//                .vendorExtensions(openApiExtensionResolver.buildExtensions())
//                .groupName("app端接口")
//                .schemes(Scheme.HTTP.toValue())
//                .apis("com.swagger.demo.controller.app");
//
//    }

    /**
     * 丰富点的
     */
    @Bean("adminApi")
    public DocDocket adminApi() {
        Map<Integer, String> responseCodes = new HashMap<>();
        Arrays.stream(ApiResultEnum.values()).forEach(enums -> {
            responseCodes.put(enums.getCode(),enums.getMessage()+"->"+enums.getDescribe());
        });

        return new DocDocket()
                .groupName("admin端接口")
                .info(new ApiInfo().title("在线文档")
                        .description("在线API文档")
                        .termsOfService("https://gitee.com/wu-zhihao/poem-solon")
                        .contact(new ApiContact().name("poem-solon")
                                .url("https://gitee.com/wu-zhihao/poem-solon")
                                .email("837713748@qq.com"))
                        .version("1.0"))
                .schemes(Scheme.HTTP.toValue(), Scheme.HTTPS.toValue())
                .globalResponseInData(true)
                .globalResult(ApiResult.class)
                .globalResponseCodes(responseCodes)
                .apis("cn.poem.solon.auth.controller") //可以加多条，以包名为单位
                .apis("cn.poem.solon.system.controller") //可以加多条，以包名为单位
                ;//.securityDefinitionInHeader("tok en");

    }
}
