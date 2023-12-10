package cn.poem.solon.admin.system.domain.entity;

import cn.poem.solon.admin.common.entity.BaseEntity;
import cn.poem.solon.admin.core.enums.CommonStatus;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 字典类型实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("字典类型实体类")
@Table(value = "poem_dict_type")
public class PoemDictType extends BaseEntity implements Serializable {

    @Id
    @ApiModelProperty("主键")
    private Long dictTypeId;

    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("字典状态")
    private CommonStatus status;

    @ApiModelProperty("备注")
    private String remark;
}
