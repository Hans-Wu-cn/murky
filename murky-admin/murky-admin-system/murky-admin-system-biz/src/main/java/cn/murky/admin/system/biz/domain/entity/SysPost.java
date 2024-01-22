package cn.murky.admin.system.biz.domain.entity;


import cn.murky.common.entity.BaseEntity;
import cn.murky.common.enums.DataScope;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Table(value = "sys_post")
@ApiModel
public class SysPost extends BaseEntity<SysPost> {
    @Id
    @ApiModelProperty("岗位id")
    private Long id;

    @ApiModelProperty("岗位名称")
    private String postName;

    @ApiModelProperty("岗位id")
    private DataScope dataScope;

}
