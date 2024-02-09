package cn.murky.tenant.system.biz.domian.entity;

import cn.murky.common.domain.entity.BaseEntity;
import cn.murky.tenant.system.api.enums.CommonStatus;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 字典数据实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("字典数据实体类")
@Table(value = "sys_dict_data")
public class SysDictData extends BaseEntity<SysDictData> {

    @Id
    @ApiModelProperty("字典编码")
    private Long dictCode;

    @ApiModelProperty("字典排序")
    private Short dictSort;

    @ApiModelProperty("字典标签")
    private String dictLabel;

    @ApiModelProperty("字典值")
    private String dictValue;

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("状态")
    private CommonStatus status;

    @ApiModelProperty("备注")
    private String remark;
}
