package cn.murky.core.config;



import cn.murky.common.enums.ApiResultEnum;
import cn.murky.core.web.ApiResult;
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
import java.util.List;
import java.util.Map;

/***
 * 接口文档配置类
 *
 * @author hans
 */
@Condition(onProperty = "${doc}") //有属性值
@Controller
@Configuration
public class DocConfig {

    @Inject("${doc.groupName}")
    private String groupName;

    @Inject("${doc.title}")
    private String title;

    @Inject("${doc.description}")
    private String description;

    @Inject("${doc.termsOfService}")
    private String termsOfService;

    @Inject("${doc.name}")
    private String name;

    @Inject("${doc.url}")
    private String url;

    @Inject("${doc.email}")
    private String email;

    @Inject("${doc.version}")
    private String version;

    @Inject("${doc.apis}")
    private List<String> apis;
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

    /**
     * 生成 dockDocket bean
     */
    @Bean("adminApi")
    @Condition(onProperty = "${doc.enable}=true") //有属性值
    public DocDocket adminApi() {
        Map<Integer, String> responseCodes = new HashMap<>();
        Arrays.stream(ApiResultEnum.values()).forEach(enums -> {
            responseCodes.put(enums.getCode(), enums.getMessage() + "->" + enums.getDescribe());
        });

        DocDocket docDocket = new DocDocket()
                .groupName(groupName)
                .info(new ApiInfo().title(title)
                        .description(description)
                        .termsOfService(termsOfService)
                        .contact(new ApiContact().name(name)
                                .url(url)
                                .email(email))
                        .version(version))
                .schemes(Scheme.HTTP.toValue(), Scheme.HTTPS.toValue())
                .globalResponseInData(true)
                .globalResult(ApiResult.class)
                .globalResponseCodes(responseCodes);
        for (String api : apis) {
            docDocket.apis(api);
        }
        return docDocket;
    }
}
