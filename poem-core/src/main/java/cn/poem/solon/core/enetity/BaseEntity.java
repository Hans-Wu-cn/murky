package cn.poem.solon.core.enetity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("公共实体")
public class BaseEntity implements Serializable {
    @ApiModelProperty("创建时间")
    protected Date createTime;

    @ApiModelProperty("修改时间")
    protected Date updateTime;

    @ApiModelProperty("创建人")
    protected Long createUser;

    @ApiModelProperty("修改人")
    protected Long updateUser;
}

