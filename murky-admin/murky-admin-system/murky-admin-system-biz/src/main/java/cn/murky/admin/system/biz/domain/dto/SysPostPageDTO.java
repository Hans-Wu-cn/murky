package cn.murky.admin.system.biz.domain.dto;

import cn.murky.admin.system.biz.domain.entity.SysPost;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel
public class SysPostPageDTO extends Page<SysPost> {
    @ApiModelProperty("岗位名称")
    private String postName;
}
