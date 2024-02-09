package cn.murky.admin.system.biz.domain.entity;

import cn.murky.common.domain.entity.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("系统参数实体类")
@Table("system_parameter")
public class SystemParameter extends BaseEntity {
    @Id
    private Long id;

    @ApiModelProperty("参数key")
    private String key;

    @ApiModelProperty("参数值")
    private String value;

    @ApiModelProperty("描述")
    private String describe;

}
