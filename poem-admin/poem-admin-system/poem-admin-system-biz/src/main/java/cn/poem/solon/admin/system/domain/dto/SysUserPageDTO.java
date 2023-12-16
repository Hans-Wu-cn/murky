package cn.poem.solon.admin.system.domain.dto;

import cn.poem.solon.admin.domin.SysUser;
import cn.poem.solon.admin.enums.Sex;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户分页查询类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("用户分页查询类")
public class SysUserPageDTO extends Page<SysUser> {

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("性别")
    private Sex sex;

    @ApiModelProperty("部门id")
    private Long deptId;
}
