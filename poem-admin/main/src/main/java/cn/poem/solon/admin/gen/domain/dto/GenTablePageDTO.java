package cn.poem.solon.admin.gen.domain.dto;

import cn.poem.solon.admin.gen.domain.entity.GenTable;
import cn.poem.solon.admin.system.domain.entity.PoemRole;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class GenTablePageDTO extends Page<GenTable> {
    @ApiModelProperty("表名")
    private String tableName;

    @ApiModelProperty("表描述")
    private String tableComment;
}
