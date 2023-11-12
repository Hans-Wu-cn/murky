package cn.poem.solon.admin.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.poem.solon.admin.auth.domain.dto.LoginDto;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.event.system.UserEvent;
import cn.poem.solon.utils.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

/***
 * 个人信息Controller
 *
 * @author hans
 */
@Controller
@Valid
@Api("个人信息控制器")
@Mapping("profile")
public class PoemProfileController extends BaseController {
    @Inject
    private UserEvent userEvent;

    @Post
    @ApiOperation("修改语言偏好")
    @Mapping("language/{language}")
    public ApiResult<String> setLanguage(@Validated @NotBlank String language) {
        ;
        return toResult(userEvent.setLanguage(language));
    }
}
