package cn.murky.admin.system.biz.domain.dto;

import cn.murky.admin.system.biz.domain.entity.SystemParameter;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 系统配置分页查询类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("系统配置分页查询类")
public class SystemParameterPageDTO extends Page<SystemParameter> {
    @ApiModelProperty("配置")
    private String key;
}
