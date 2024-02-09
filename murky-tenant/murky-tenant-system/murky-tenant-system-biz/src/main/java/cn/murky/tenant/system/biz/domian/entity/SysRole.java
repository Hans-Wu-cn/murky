package cn.murky.tenant.system.biz.domian.entity;

import cn.murky.common.domain.entity.BaseEntity;
import cn.murky.common.enums.DataScope;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Table(value = "sys_role")
public class SysRole extends BaseEntity<SysRole> {
    @Id
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色码")
    private String roleCode;

    @ApiModelProperty("数据权限")
    private DataScope dataScope;

    @ApiModelProperty("部门Id")
    private Long fkDeptId;

    @ApiModelProperty("描述")
    private String describe;
}
